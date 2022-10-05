package com.arpansircar.notes_app.presentation.callbacks

import androidx.fragment.app.FragmentActivity
import com.arpansircar.notes_app.domain.models.Note

interface DialogCallback {
    fun onPositiveButtonClicked(
        dialogType: String,
        dialogContext: FragmentActivity,
        note: Note,
        notePosition: Int
    )
}