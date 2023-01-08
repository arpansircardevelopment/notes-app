package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentAccountBinding
import com.arpansircar.notes_app.di.ApplicationContainerRoot
import com.arpansircar.notes_app.di.HomeContainerRoot
import com.arpansircar.notes_app.presentation.viewmodel.AccountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser

class AccountFragment : Fragment() {

    private var appContainer: ApplicationContainerRoot? = null
    private var homeContainerRoot: HomeContainerRoot? = null

    private var binding: FragmentAccountBinding? = null
    private lateinit var viewModel: AccountViewModel
    private var firebaseAuth: FirebaseAuth? = null
    private var authStateListener: AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as NotesApplication).appContainer
        homeContainerRoot = HomeContainerRoot(
            appContainer?.notesDao!!, appContainer?.datastoreContainer!!
        )

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        viewModel = ViewModelProvider(
            this, homeContainerRoot?.accountViewModelFactory!!
        )[AccountViewModel::class.java]

        firebaseAuth = homeContainerRoot?.firebaseContainerRoot?.firebaseAuth
        authStateListener = AuthStateListener { auth ->
            auth.currentUser?.let { it ->
                setData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        viewModel.fetchCurrentUserDetails()?.let { setData(it) }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rlEditProfile?.setOnClickListener {
            findNavController().navigate(R.id.action_account_to_edit_details_list)
        }

        binding?.rlSignOut?.setOnClickListener {
            viewModel.userSignOut()
            findNavController().navigate(R.id.action_account_to_login)
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth?.addAuthStateListener { authStateListener }
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth?.removeAuthStateListener { authStateListener }
    }

    override fun onDestroy() {
        super.onDestroy()
        firebaseAuth = null
        homeContainerRoot = null
        appContainer = null
    }

    private fun setData(currentUser: FirebaseUser) {
        currentUser.let {
            binding?.tvUserName?.text = it.displayName
            binding?.tvUserEmail?.text = it.email
            binding?.avProfileImage?.avatarInitials = it.displayName?.get(0)?.toString()
        }
    }
}