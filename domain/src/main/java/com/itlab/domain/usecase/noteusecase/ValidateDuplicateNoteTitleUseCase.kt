package com.itlab.domain.usecase.noteusecase

import com.itlab.domain.repository.NotesRepository
import kotlinx.coroutines.flow.first

class ValidateDuplicateNoteTitleUseCase(
    private val repo: NotesRepository,
) {
    /** @return true when another note in the same folder already has this title. */
    suspend operator fun invoke(
        title: String,
        folderId: String?,
        excludeNoteId: String,
    ): Boolean {
        val normalizedTitle = title.trim()
        if (normalizedTitle.isEmpty()) return false
        return repo.observeNotes().first().any { existing ->
            existing.id != excludeNoteId &&
                existing.folderId == folderId &&
                existing.title.trim().equals(normalizedTitle, ignoreCase = true)
        }
    }
}
