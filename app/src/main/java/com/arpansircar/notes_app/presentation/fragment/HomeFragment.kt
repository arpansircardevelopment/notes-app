package com.arpansircar.notes_app.presentation.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.ConstantsBase.DIALOG_TYPE_DELETE
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_ID
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE_ADD
import com.arpansircar.notes_app.common.ConstantsBase.NOTE_TYPE_EDIT
import com.arpansircar.notes_app.databinding.FragmentHomeBinding
import com.arpansircar.notes_app.domain.models.Note
import com.arpansircar.notes_app.presentation.adapter.HomeAdapter
import com.arpansircar.notes_app.presentation.base.BaseFragment
import com.arpansircar.notes_app.presentation.callbacks.DialogCallback
import com.arpansircar.notes_app.presentation.callbacks.HomeScreenCallback
import com.arpansircar.notes_app.presentation.utils.DialogManager
import com.arpansircar.notes_app.presentation.utils.ScreensNavigator
import com.arpansircar.notes_app.presentation.viewmodel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeFragment : BaseFragment(),
    HomeAdapter.NotePressedListener,
    DialogCallback,
    HomeScreenCallback {

    lateinit var viewModel: HomeViewModel
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var dialogManager: DialogManager
    lateinit var screensNavigator: ScreensNavigator

    private var binding: FragmentHomeBinding? = null
    private var adapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeInjector.inject(this)
        initializeBackPressedDispatcher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).also {
            it.btAdd.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
        }
        setUser(firebaseAuth.currentUser)
        syncAndFetchNotes()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        setObservers()
    }

    private fun setOnClickListeners() {
        binding?.btAdd?.setOnClickListener {
            screensNavigator.navigateWithBundle(
                R.id.action_home_to_add_edit,
                bundleOf(NOTE_TYPE to NOTE_TYPE_ADD, NOTE_ID to null)
            )
        }

        binding?.avProfileImage?.setOnClickListener {
            screensNavigator.navigateToScreen(R.id.action_home_to_account)
        }
    }

    private fun setObservers() {
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
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }

            dialogManager.hideProgressDialog()
        }

        viewModel.syncedLiveData.observe(viewLifecycleOwner) {
            dialogManager.hideProgressDialog()
            if (!it) {
                dialogManager.showProgressDialog(
                    childFragmentManager,
                    getString(R.string.loading_your_notes)
                )
                viewModel.downloadNotes()
            }
        }
    }

    private fun syncAndFetchNotes() {
        viewModel.isSynced()
        viewModel.fetchNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onNotePressed(note: Note, view: View, position: Int) {
        dialogManager.displayHomeScreenBottomSheet(
            childFragmentManager,
            this,
            note,
            position
        )
    }

    override fun onPositiveButtonClicked(
        dialogType: String,
        dialogContext: FragmentActivity,
        note: Note,
        notePosition: Int
    ) {
        if (dialogType == DIALOG_TYPE_DELETE) {
            viewModel.deleteNote(note)
            adapter?.notifyItemRemoved(notePosition)
        }
    }

    private fun setUser(currentUser: FirebaseUser?) {
        currentUser.let {
            binding?.avProfileImage?.avatarInitials = it?.displayName?.get(0)?.toString()
        }
    }

    override fun onEditNoteOptionClicked(noteID: Int) {
        screensNavigator.navigateWithBundle(
            R.id.action_home_to_add_edit,
            bundleOf(
                NOTE_TYPE to NOTE_TYPE_EDIT,
                NOTE_ID to noteID
            )
        )
    }

    override fun onDeleteNoteOptionClicked(note: Note, position: Int) {
        dialogManager.displayDeleteDialog(
            childFragmentManager,
            this,
            DIALOG_TYPE_DELETE,
            note,
            position
        )
    }
}