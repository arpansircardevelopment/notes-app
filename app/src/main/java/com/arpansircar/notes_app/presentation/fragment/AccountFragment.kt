package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentAccountBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.arpansircar.notes_app.di.HomeContainer
import com.arpansircar.notes_app.presentation.viewmodel.AccountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser

class AccountFragment : Fragment() {

    private var appContainer: ApplicationContainer? = null
    private var homeContainer: HomeContainer? = null

    private var binding: FragmentAccountBinding? = null
    private lateinit var viewModel: AccountViewModel
    private var firebaseAuth: FirebaseAuth? = null
    private var authStateListener: AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as NotesApplication).appContainer
        homeContainer = HomeContainer(
            appContainer?.notesDao!!, appContainer?.datastoreContainer!!
        )

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

        viewModel = ViewModelProvider(
            this, homeContainer?.accountViewModelFactory!!
        )[AccountViewModel::class.java]

        firebaseAuth = homeContainer?.firebaseContainer?.firebaseAuth
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
        homeContainer = null
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