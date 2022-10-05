package com.arpansircar.notes_app.presentation.utils

import androidx.fragment.app.FragmentManager
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.callbacks.DialogCallback
import com.arpansircar.notes_app.presentation.utils.dialogs.DeleteDialog

class DialogManager {

    // function for displaying delete dialogs
    fun displayDeleteDialog(
        fm: FragmentManager,
        callback: DialogCallback,
        dialogType: String,
        note: Note,
        notePosition: Int
    ) {
        DeleteDialog(callback, dialogType, note, notePosition).also {
            it.show(fm, DeleteDialog.TAG)
        }
    }
}