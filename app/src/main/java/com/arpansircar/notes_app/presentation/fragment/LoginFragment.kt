package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.FragmentLoginBinding
import com.arpansircar.notes_app.presentation.base.BaseFragment
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.clearTextFieldFocus
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.enableViewElements
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.shouldShowProgressUI
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.showShortToast
import com.arpansircar.notes_app.presentation.utils.ListenerUtils.getWatcher
import com.arpansircar.notes_app.presentation.utils.ScreensNavigator
import com.arpansircar.notes_app.presentation.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser

class LoginFragment : BaseFragment(), AuthStateListener {

    // Dependencies
    var currentUser: FirebaseUser? = null
    var firebaseAuth: FirebaseAuth? = null
    lateinit var viewModel: LoginViewModel
    lateinit var screensNavigator: ScreensNavigator

    // Data Structures and Variables
    private var email: String? = null
    private var password: String? = null
    private var userDisplayName: String? = null
    private var binding: FragmentLoginBinding? = null
    private val watcherHashSet = HashSet<TextWatcher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authInjector.inject(this)
        initializeNavigation()
        initializeBackPressedDispatcher(this@LoginFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.signUpPrompt?.setOnClickListener {
            navigateToScreen(R.id.action_login_to_signup)
        }

        viewModel.responseObserver.observe(viewLifecycleOwner) {
            shouldShowProgressUI(false)
            showUIElements(true)

            if (it != null) {
                showShortToast(it)
                return@observe
            }

            showShortToast(getString(R.string.logged_in))

            val navID: Int = if (userDisplayName == null) {
                R.id.action_login_to_user_details
            } else {
                R.id.action_login_to_home
            }

            navigateToScreen(navID)
        }
    }

    override fun onStart() {
        super.onStart()
        registerListeners()
    }

    override fun onResume() {
        super.onResume()

        binding?.btLogin?.setOnClickListener {
            showUIElements(false)
            shouldShowProgressUI(true)
            clearTextFieldFocus()

            if (isDataValid()) {
                viewModel.userLogin(email!!, password!!)
            } else {
                showUIElements(true)
                shouldShowProgressUI(false)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initializeNavigation() {
        if (currentUser == null) return

        userDisplayName = firebaseAuth?.currentUser?.displayName

        if (userDisplayName.isNullOrEmpty()) {
            navigateToScreen(R.id.fragment_user_details)
        } else {
            navigateToScreen(R.id.fragment_home)
        }
    }

    private fun showUIElements(isEnabled: Boolean) {
        enableViewElements(listOf(binding?.btLogin, binding?.signUpPrompt), isEnabled)
    }

    private fun shouldShowProgressUI(isVisible: Boolean) {
        binding?.cpi?.shouldShowProgressUI(isVisible)
    }

    private fun clearTextFieldFocus() {
        clearTextFieldFocus(listOf(binding?.tilEmail, binding?.tilPassword))
    }

    private fun isDataValid(): Boolean {
        email = binding?.etEmail?.text?.toString()
        password = binding?.etPassword?.text?.toString()

        var isValid = true

        if (!viewModel.isEmailValid(email)) {
            binding?.tilEmail?.error = getString(R.string.invalid_email)
            isValid = false
        }

        if (!viewModel.isPasswordValid(password)) {
            binding?.tilPassword?.error = getString(R.string.invalid_password)
            isValid = false
        }

        return isValid
    }

    private fun registerListeners() {
        firebaseAuth?.addAuthStateListener(this)
        binding?.etEmail?.addTextChangedListener(getWatcher(binding?.tilEmail, watcherHashSet))
        binding?.etPassword?.addTextChangedListener(
            getWatcher(binding?.tilPassword, watcherHashSet)
        )
    }

    private fun unregisterListener() {
        firebaseAuth?.removeAuthStateListener(this)
        watcherHashSet.forEach {
            binding?.etEmail?.removeTextChangedListener(it)
            binding?.etPassword?.removeTextChangedListener(it)
        }
        watcherHashSet.clear()
    }

    override fun onAuthStateChanged(currentUser: FirebaseAuth) {
        userDisplayName = currentUser.currentUser?.displayName
    }

    private fun navigateToScreen(navID: Int) {
        screensNavigator.navigateToScreen(navID, this)
    }
}