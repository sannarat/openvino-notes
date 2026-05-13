package com.itlab.domain.usecase.noteusecase

import com.itlab.domain.model.Note
import com.itlab.domain.repository.NotesRepository
import kotlinx.coroutines.flow.first
import java.util.UUID
import kotlin.time.Clock

class CreateNoteUseCase(
    private val repo: NotesRepository,
) {
    suspend operator fun invoke(note: Note): Result<String> =
        runCatching {
            val normalizedTitle = note.title.trim()
            val hasDuplicateTitle =
                repo.observeNotes().first().any { existing ->
                    existing.folderId == note.folderId &&
                        existing.title.trim().equals(normalizedTitle, ignoreCase = true)
                }
            require(!hasDuplicateTitle) { "Note with title '$normalizedTitle' already exists in this folder" }
            val now = Clock.System.now()

            val note =
                note.copy(
                    id = UUID.randomUUID().toString(),
                    createdAt = now,
                    updatedAt = now,
                )
            repo.createNote(note)
        }
}
