package com.arpansircar.notes_app.presentation.utils.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.arpansircar.notes_app.databinding.DialogDeleteNoteBinding
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.callbacks.DialogCallback

class DeleteDialog(
    private val callback: DialogCallback,
    private val dialogType: String,
    private val note: Note,
    private val notePosition: Int
) : DialogFragment() {

    private var binding: DialogDeleteNoteBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogDeleteNoteBinding.inflate(layoutInflater)
        setupListeners()

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding?.root).create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setupListeners() {
        binding?.yesButton?.setOnClickListener {
            callback.onPositiveButtonClicked(
                dialogType,
                requireActivity(),
                note,
                notePosition
            )

            dismiss()
        }

        binding?.noButton?.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "DeleteDialog"
    }
}