package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentHomeBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.arpansircar.notes_app.di.HomeContainer
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.adapter.HomeAdapter
import com.arpansircar.notes_app.presentation.viewmodel.HomeViewModel

class HomeFragment : Fragment(), HomeAdapter.NotePressedListener {

    private var appContainer: ApplicationContainer? = null
    private var homeContainer: HomeContainer? = null

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private var adapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as NotesApplication).appContainer
        homeContainer = appContainer?.homeContainer
        viewModel = ViewModelProvider(
            this,
            homeContainer?.homeViewModelFactory!!
        )[HomeViewModel::class.java]

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
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

            adapter = HomeAdapter(it, this)
            binding?.rvNotes?.apply {
                this.adapter = this@HomeFragment.adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        binding?.btAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_add_edit)
        }
    }

    private fun showMenu(view: View, @MenuRes menuRes: Int, note: Note, position: Int) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(menuRes, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.item_edit -> {
                }

                R.id.item_delete -> {
                    viewModel.deleteNote(note)
                    adapter?.notifyItemRemoved(position)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
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

    override fun onNotePressed(note: Note, view: View, position: Int) {
        showMenu(view, R.menu.note_options_menu, note, position)
    }
}