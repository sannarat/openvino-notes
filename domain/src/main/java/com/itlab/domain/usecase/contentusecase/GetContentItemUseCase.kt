package com.itlab.domain.usecase.contentusecase

import com.itlab.domain.model.ContentItem
import com.itlab.domain.repository.NotesRepository
import com.itlab.domain.usecase.requireNotBlank

class GetContentItemUseCase(
    private val notesRepository: NotesRepository,
) {
    suspend operator fun invoke(
        noteId: String,
        itemId: String,
    ): Result<ContentItem?> =
        runCatching {
            requireNotBlank(noteId, "Note id")
            requireNotBlank(itemId, "Content item id")
            val note =
                notesRepository.getNoteById(noteId)
                    ?: throw IllegalArgumentException("Note not found: $noteId")

            note.contentItems.find { it.id == itemId }
        }
}
