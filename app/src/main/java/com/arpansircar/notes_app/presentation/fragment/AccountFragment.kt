package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.FragmentAccountBinding
import com.arpansircar.notes_app.presentation.base.BaseFragment
import com.arpansircar.notes_app.presentation.utils.ScreensNavigator
import com.arpansircar.notes_app.presentation.viewmodel.AccountViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser

class AccountFragment : BaseFragment() {

    lateinit var viewModel: AccountViewModel
    var firebaseAuth: FirebaseAuth? = null
    lateinit var screensNavigator: ScreensNavigator

    private var binding: FragmentAccountBinding? = null
    private var authStateListener: AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeInjector.inject(this)
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
            screensNavigator.navigateToScreen(R.id.action_account_to_edit_details_list)
        }

        binding?.rlSignOut?.setOnClickListener {
            viewModel.userSignOut()
            screensNavigator.navigateToScreen(R.id.action_account_to_login)
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

    private fun setData(currentUser: FirebaseUser) {
        currentUser.let {
            binding?.tvUserName?.text = it.displayName
            binding?.tvUserEmail?.text = it.email
            binding?.avProfileImage?.avatarInitials = it.displayName?.get(0)?.toString()
        }
    }
}