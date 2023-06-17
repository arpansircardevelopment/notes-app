package com.arpansircar.notes_app.presentation.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_ID
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE_ADD
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE_EDIT
import com.arpansircar.notes_app.databinding.FragmentAddEditNoteBinding
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.base.BaseFragment
import com.arpansircar.notes_app.presentation.utils.ScreensNavigator
import com.arpansircar.notes_app.presentation.viewmodel.AddEditNoteViewModel
import java.util.*

class AddEditNoteFragment : BaseFragment() {

    lateinit var viewModel: AddEditNoteViewModel
    lateinit var screensNavigator: ScreensNavigator

    private var binding: FragmentAddEditNoteBinding? = null
    private var noteType: String? = null
    private var noteID: Int? = null
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeInjector.inject(this)
        noteType = arguments?.getString(NOTE_TYPE)
        noteID = arguments?.getInt(NOTE_ID, -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false).also {
            it.btInsert.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(), R.color.white
                )
            )
        }
        setNoteData()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btInsert?.setOnClickListener {
            if (noteType == NOTE_TYPE_ADD) {
                insertNote()
                return@setOnClickListener
            }

            if (noteType == NOTE_TYPE_EDIT) {
                updateNote()
                return@setOnClickListener
            }
        }

        binding?.btBack?.setOnClickListener {
            screensNavigator.navigateUp()
        }

        binding?.btDelete?.setOnClickListener {
            note?.let { viewModel.deleteNote(it) }
            screensNavigator.navigateUp()
        }

        viewModel.notesLiveData.observe(viewLifecycleOwner) {
            screensNavigator.navigateUp()
        }

        viewModel.updateNoteLiveData.observe(viewLifecycleOwner) {
            screensNavigator.navigateUp()
        }

        viewModel.fetchNotesLiveData.observe(viewLifecycleOwner) {
            note = it
            binding?.etTitle?.setText(it.noteTitle)
            binding?.etDetail?.setText(it.noteDetail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setNoteData() {
        if (noteType == NOTE_TYPE_EDIT && noteID != -1) {
            viewModel.getNote(noteID!!)
            return
        }
    }

    private fun insertNote() {
        if (validateData()) {
            val note = Note(
                noteUUID = UUID.randomUUID().toString(),
                noteTitle = binding?.etTitle?.text?.toString()!!,
                noteDetail = binding?.etDetail?.text?.toString()!!,
                noteCreatedAt = Calendar.getInstance().timeInMillis
            )
            viewModel.addNote(note)
        }
    }

    private fun updateNote() {
        if (validateData()) {
            val updatedNote = Note(
                id = note?.id,
                noteUUID = note?.noteUUID!!,
                noteTitle = binding?.etTitle?.text?.toString()!!,
                noteDetail = binding?.etDetail?.text?.toString()!!,
                noteCreatedAt = note?.noteCreatedAt!!
            )
            viewModel.updateNote(updatedNote)
        }
    }

    private fun validateData(): Boolean {
        return viewModel.validateData(
            binding?.etTitle, binding?.etDetail
        )
    }
}