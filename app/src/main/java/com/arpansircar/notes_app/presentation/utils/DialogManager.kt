package com.arpansircar.notes_app.presentation.utils

import androidx.fragment.app.FragmentManager
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.callbacks.DialogCallback
import com.arpansircar.notes_app.presentation.callbacks.HomeScreenCallback
import com.arpansircar.notes_app.presentation.utils.dialogs.DeleteDialog
import com.arpansircar.notes_app.presentation.utils.dialogs.HomeScreenBottomSheet
import com.arpansircar.notes_app.presentation.utils.dialogs.ProgressDialog

class DialogManager {

    private var progressDialog: ProgressDialog? = null

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

    // function for displaying home screen bottom sheet
    fun displayHomeScreenBottomSheet(
        fragmentManager: FragmentManager,
        callback: HomeScreenCallback,
        note: Note,
        position: Int
    ) {
        HomeScreenBottomSheet(callback, note, position).also {
            it.show(
                fragmentManager,
                HomeScreenBottomSheet.CLASS_NAME
            )
        }
    }

    // function for displaying progress dialogs
    fun showProgressDialog(fm: FragmentManager, dialogMessage: String?) {
        progressDialog = ProgressDialog(dialogMessage)
        progressDialog?.isCancelable = false
        progressDialog?.show(fm, ProgressDialog.TAG)
    }

    // function for hiding progress dialogs
    fun hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }
}