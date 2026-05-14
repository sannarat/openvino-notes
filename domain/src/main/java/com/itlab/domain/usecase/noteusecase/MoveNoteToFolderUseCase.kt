package com.itlab.domain.usecase.noteusecase

import com.itlab.domain.repository.NoteFolderRepository
import com.itlab.domain.repository.NotesRepository
import com.itlab.domain.usecase.requireNotBlank
import kotlin.time.Clock

class MoveNoteToFolderUseCase(
    private val notesRepo: NotesRepository,
    private val folderRepo: NoteFolderRepository,
) {
    suspend operator fun invoke(
        folderId: String,
        noteId: String,
    ): Result<Unit> =
        runCatching {
            requireNotBlank(noteId, "Note id")
            requireNotBlank(folderId, "Folder id")
            requireNotNull(folderRepo.getFolderById(folderId)) { "Folder not found: $folderId" }
            val note = notesRepo.getNoteById(noteId) ?: throw IllegalArgumentException("Note not found: $noteId")
            val updated = note.copy(folderId = folderId, updatedAt = Clock.System.now())
            notesRepo.updateNote(updated)
        }
}
