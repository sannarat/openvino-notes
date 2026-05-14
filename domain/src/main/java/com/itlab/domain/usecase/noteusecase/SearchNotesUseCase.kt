package com.itlab.domain.usecase.noteusecase

import com.itlab.domain.model.ContentItem
import com.itlab.domain.model.Note
import com.itlab.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchNotesUseCase(
    private val repo: NotesRepository,
) {
    operator fun invoke(query: String): Flow<List<Note>> {
        val normalizedQuery = query.trim().lowercase()
        if (normalizedQuery.isBlank()) return repo.observeNotes()

        return repo.observeNotes().map { notes ->
            notes.filter { note -> note.matches(normalizedQuery) }
        }
    }

    private fun Note.matches(normalizedQuery: String): Boolean {
        val titleMatch = title.contains(normalizedQuery, ignoreCase = true)
        val contentMatch =
            contentItems.any { item ->
                item is ContentItem.Text && item.text.contains(normalizedQuery, ignoreCase = true)
            }

        return titleMatch || contentMatch
    }
}
