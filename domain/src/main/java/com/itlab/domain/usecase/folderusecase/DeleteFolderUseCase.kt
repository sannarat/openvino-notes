package com.itlab.domain.usecase.folderusecase

import com.itlab.domain.repository.NoteFolderRepository
import com.itlab.domain.repository.NotesRepository
import com.itlab.domain.usecase.requireNotBlank
import kotlinx.coroutines.flow.first

class DeleteFolderUseCase(
    private val repo: NoteFolderRepository,
    private val notesRepository: NotesRepository,
) {
    suspend operator fun invoke(id: String): Result<Unit> =
        runCatching {
            requireNotBlank(id, "Folder id")
            require(id != "all") { "System folder 'all' cannot be deleted" }
            notesRepository
                .observeNotesByFolder(id)
                .first()
                .forEach { note ->
                    notesRepository.deleteNote(note.id)
                }
            repo.deleteFolder(id)
        }
}
