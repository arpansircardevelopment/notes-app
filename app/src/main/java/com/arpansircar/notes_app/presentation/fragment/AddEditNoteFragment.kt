package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_ID
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE_ADD
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE_EDIT
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentAddEditNoteBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.arpansircar.notes_app.di.HomeContainer
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.viewmodel.AddEditNoteViewModel
import java.util.*

class AddEditNoteFragment : Fragment() {

    private var appContainer: ApplicationContainer? = null
    private var homeContainer: HomeContainer? = null

    private var binding: FragmentAddEditNoteBinding? = null
    private lateinit var viewModel: AddEditNoteViewModel
    private var noteType: String? = null
    private var noteID: Int? = null
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as NotesApplication).appContainer
        homeContainer = appContainer?.homeContainer
        viewModel = ViewModelProvider(
            this,
            homeContainer?.addEditNoteViewModelFactory!!
        )[AddEditNoteViewModel::class.java]

        noteType = arguments?.getString(NOTE_TYPE)
        noteID = arguments?.getInt(NOTE_ID, -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
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

        viewModel.notesLiveData.observe(viewLifecycleOwner) {
            NavHostFragment.findNavController(this@AddEditNoteFragment).navigateUp()
        }

        viewModel.updateNoteLiveData.observe(viewLifecycleOwner) {
            NavHostFragment.findNavController(this@AddEditNoteFragment).navigateUp()
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

    override fun onDestroy() {
        super.onDestroy()
        homeContainer = null
        appContainer = null
    }

    private fun setNoteData() {
        if (noteType == NOTE_TYPE_EDIT && noteID != -1) {
            binding?.btInsert?.text = getString(R.string.update_note)
            viewModel.getNote(noteID!!)
            return
        }
    }

    private fun insertNote() {
        if (validateData()) {
            val note = Note(
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
                noteTitle = binding?.etTitle?.text?.toString()!!,
                noteDetail = binding?.etDetail?.text?.toString()!!,
                noteCreatedAt = note?.noteCreatedAt!!
            )
            viewModel.updateNote(updatedNote)
        }
    }

    private fun validateData(): Boolean {
        return viewModel.validateData(
            binding?.etTitle,
            binding?.etDetail
        )
    }
}