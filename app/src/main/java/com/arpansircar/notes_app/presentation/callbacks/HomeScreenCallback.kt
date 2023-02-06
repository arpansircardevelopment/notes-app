package com.arpansircar.notes_app.presentation.callbacks

import com.arpansircar.notes_app.domain.models.Note

interface HomeScreenCallback {
    fun onEditNoteOptionClicked(noteID: Int)
    fun onDeleteNoteOptionClicked(note: Note, position: Int)
}