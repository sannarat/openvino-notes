package com.itlab.domain.usecase.contentusecase

import com.itlab.domain.repository.NotesRepository
import com.itlab.domain.usecase.requireNotBlank
import kotlin.time.Clock

class DeleteContentItemUseCase(
    private val notesRepository: NotesRepository,
) {
    suspend operator fun invoke(
        noteId: String,
        itemId: String,
    ): Result<Unit> =
        runCatching {
            requireNotBlank(noteId, "Note id")
            requireNotBlank(itemId, "Content item id")
            val note =
                notesRepository.getNoteById(noteId)
                    ?: throw IllegalArgumentException("Note not found: $noteId")

            val updated =
                note.copy(
                    contentItems = note.contentItems.filterNot { it.id == itemId },
                    updatedAt = Clock.System.now(),
                )
            notesRepository.updateNote(updated)
        }
}
