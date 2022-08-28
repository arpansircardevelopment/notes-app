package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentAddEditNoteBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.arpansircar.notes_app.di.HomeContainer
import com.arpansircar.notes_app.presentation.viewmodel.AddEditNoteViewModel

class AddEditNoteFragment : Fragment() {

    private var appContainer: ApplicationContainer? = null
    private var homeContainer: HomeContainer? = null

    private var binding: FragmentAddEditNoteBinding? = null
    private lateinit var viewModel: AddEditNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as NotesApplication).appContainer
        homeContainer = appContainer?.homeContainer
        viewModel = ViewModelProvider(
            this,
            homeContainer?.addEditNoteViewModelFactory!!
        )[AddEditNoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
}