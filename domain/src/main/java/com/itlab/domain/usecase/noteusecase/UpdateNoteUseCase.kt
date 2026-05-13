package com.itlab.domain.usecase.noteusecase

import com.itlab.domain.model.Note
import com.itlab.domain.repository.NotesRepository
import kotlinx.coroutines.flow.first
import kotlin.time.Clock

class UpdateNoteUseCase(
    private val repo: NotesRepository,
) {
    suspend operator fun invoke(note: Note): Result<Unit> =
        runCatching {
            val normalizedTitle = note.title.trim()
            val hasDuplicateTitle =
                repo.observeNotes().first().any { existing ->
                    existing.id != note.id &&
                        existing.folderId == note.folderId &&
                        existing.title.trim().equals(normalizedTitle, ignoreCase = true)
                }
            require(!hasDuplicateTitle) { "Note with title '$normalizedTitle' already exists in this folder" }
            val note = note.copy(updatedAt = Clock.System.now())
            repo.updateNote(note)
        }
}
