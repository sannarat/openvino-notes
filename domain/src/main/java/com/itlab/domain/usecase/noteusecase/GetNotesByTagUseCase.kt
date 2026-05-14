package com.itlab.domain.usecase.noteusecase

import com.itlab.domain.model.Note
import com.itlab.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesByTagUseCase(
    private val repo: NotesRepository,
) {
    operator fun invoke(tag: String): Flow<List<Note>> {
        val normalizedTag = tag.trim().lowercase()

        return repo.observeNotes().map { notes ->
            if (normalizedTag.isBlank()) {
                notes
            } else {
                notes.filter { note ->
                    note.tags.any { it.lowercase() == normalizedTag }
                }
            }
        }
    }
}
