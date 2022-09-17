package com.arpansircar.notes_app.presentation.utils

import androidx.fragment.app.FragmentManager
import com.arpansircar.notes_app.presentation.utils.dialogs.DeleteDialog

class DialogManager {

    // function for displaying delete dialogs
    fun displayDeleteDialog(fm: FragmentManager) {
        val deleteDialog = DeleteDialog()
        deleteDialog.show(fm, DeleteDialog.TAG)
    }
}