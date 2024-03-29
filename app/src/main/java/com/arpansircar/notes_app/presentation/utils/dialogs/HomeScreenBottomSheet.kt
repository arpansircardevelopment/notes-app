package com.arpansircar.notes_app.presentation.utils.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arpansircar.notes_app.databinding.FragmentHomeBottomSheetBinding
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.callbacks.HomeScreenCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeScreenBottomSheet(
    private val callback: HomeScreenCallback,
    private val note: Note,
    private val position: Int
) : BottomSheetDialogFragment() {

    private var binding: FragmentHomeBottomSheetBinding? = null

    companion object {
        val CLASS_NAME: String = this::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBottomSheetBinding.inflate(inflater, container, false)
        setOnClickListeners(binding)
        return binding?.root
    }

    private fun setOnClickListeners(binding: FragmentHomeBottomSheetBinding?) {
        binding?.llEdit?.setOnClickListener {
            callback.onEditNoteOptionClicked(note.id!!)
            dismiss()
        }

        binding?.llDelete?.setOnClickListener {
            callback.onDeleteNoteOptionClicked(note, position)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}