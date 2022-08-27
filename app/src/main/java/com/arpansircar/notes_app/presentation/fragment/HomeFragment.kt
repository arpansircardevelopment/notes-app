package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arpansircar.notes_app.common.ManualDiPracticeApplication
import com.arpansircar.notes_app.databinding.FragmentHomeBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.arpansircar.notes_app.di.HomeContainer
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.adapter.HomeAdapter
import com.arpansircar.notes_app.presentation.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var appContainer: ApplicationContainer? = null
    private var homeContainer: HomeContainer? = null

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as ManualDiPracticeApplication).appContainer
        homeContainer = appContainer?.homeContainer
        viewModel = ViewModelProvider(
            this,
            homeContainer?.homeViewModelFactory!!
        )[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.fetchNotes()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.notesLiveData.observe(viewLifecycleOwner) {

            if (it.isEmpty()) {
                binding?.llEmpty?.visibility = View.VISIBLE
                binding?.rvNotes?.visibility = View.GONE
                return@observe
            }

            binding?.llEmpty?.visibility = View.GONE
            binding?.rvNotes?.visibility = View.VISIBLE

            val adapter = HomeAdapter(it)
            binding?.rvNotes?.apply {
                setAdapter(adapter)
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        binding?.btAdd?.setOnClickListener {
            viewModel.addNote(
                Note(
                    noteTitle = "Arpan",
                    noteDetail = "Test note details",
                    noteCreatedAt = 10010001010
                )
            )
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
}