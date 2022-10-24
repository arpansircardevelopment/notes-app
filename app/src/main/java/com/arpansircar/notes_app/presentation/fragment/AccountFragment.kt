package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentAccountBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.arpansircar.notes_app.di.HomeContainer
import com.arpansircar.notes_app.presentation.viewmodel.AccountViewModel
import com.google.firebase.auth.FirebaseUser

class AccountFragment : Fragment() {

    private var appContainer: ApplicationContainer? = null
    private var homeContainer: HomeContainer? = null

    private var binding: FragmentAccountBinding? = null
    private lateinit var viewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as NotesApplication).appContainer
        homeContainer = appContainer?.homeContainer

        viewModel = ViewModelProvider(
            this, homeContainer?.accountViewModelFactory!!
        )[AccountViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        setData()
        return binding?.root
    }

    private fun setData() {
        val currentUser: FirebaseUser? = viewModel.fetchCurrentUserDetails()
        currentUser?.let {
            binding?.tvUserName?.text = it.displayName
            binding?.tvUserEmail?.text = it.email
            binding?.avProfileImage?.avatarInitials = it.displayName?.get(0)?.toString()
        }
    }
}