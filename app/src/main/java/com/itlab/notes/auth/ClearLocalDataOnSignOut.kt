package com.itlab.notes.auth

import com.itlab.domain.usecase.folderusecase.DeleteFolderUseCase
import com.itlab.domain.usecase.folderusecase.ObserveFoldersUseCase
import com.itlab.domain.usecase.noteusecase.DeleteNoteUseCase
import com.itlab.domain.usecase.noteusecase.ObserveNotesUseCase
import kotlinx.coroutines.flow.first

/** Removes all local notes and folders when the user signs out (app-layer only). */
class ClearLocalDataOnSignOut(
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val observeFoldersUseCase: ObserveFoldersUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
) {
    suspend operator fun invoke() {
        observeNotesUseCase().first().forEach { note ->
            deleteNoteUseCase(note.id)
        }
        observeFoldersUseCase().first().forEach { folder ->
            deleteFolderUseCase(folder.id)
        }
    }
}
