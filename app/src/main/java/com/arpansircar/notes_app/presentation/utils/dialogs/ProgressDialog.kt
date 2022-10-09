package com.arpansircar.notes_app.presentation.utils.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.arpansircar.notes_app.databinding.DialogProgressBinding

class ProgressDialog(
    private val dialogMessage: String?
) : DialogFragment() {

    private var binding: DialogProgressBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogProgressBinding.inflate(layoutInflater)
        setData()

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(binding?.root).create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setData() {
        dialogMessage?.let {
            binding?.tvTitle?.text = it
        }
    }

    companion object {
        const val TAG = "ProgressDialog"
    }
}