package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.FragmentSignupBinding
import com.arpansircar.notes_app.presentation.base.BaseFragment
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.clearTextFieldFocus
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.enableViewElements
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.shouldShowProgressUI
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.showShortToast
import com.arpansircar.notes_app.presentation.utils.ListenerUtils.getWatcher
import com.arpansircar.notes_app.presentation.utils.ScreensNavigator
import com.arpansircar.notes_app.presentation.viewmodel.LoginViewModel
import com.arpansircar.notes_app.presentation.viewmodel.SignupViewModel
import com.google.firebase.auth.FirebaseUser

class SignupFragment : BaseFragment() {

    // Dependencies
    lateinit var viewModel: SignupViewModel
    lateinit var screensNavigator: ScreensNavigator

    // Data Structures and Variables
    private var email: String? = null
    private var password: String? = null
    private var binding: FragmentSignupBinding? = null
    private val watcherHashSet = HashSet<TextWatcher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authInjector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.loginPrompt?.setOnClickListener {
            navigateToScreen(R.id.action_signup_to_login)
        }

        viewModel.responseObserver.observe(viewLifecycleOwner) {
            shouldShowProgressUI(false)
            showUIElements(true)

            if (it != null) {
                showShortToast(it)
                return@observe
            }

            showShortToast(getString(R.string.account_created))
            navigateToScreen(R.id.action_signup_to_user_details)

        }
    }

    override fun onStart() {
        super.onStart()
        registerListeners()
    }

    override fun onResume() {
        super.onResume()
        binding?.btSignup?.setOnClickListener {
            showUIElements(false)
            shouldShowProgressUI(true)
            clearTextFieldFocus()

            if (isDataValid()) {
                viewModel.createUser(email!!, password!!)
            } else {
                showUIElements(true)
                shouldShowProgressUI(false)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun isDataValid(): Boolean {
        var isValid = true

        email = binding?.etEmail?.text?.toString()
        password = binding?.etPassword?.text?.toString()
        val confirmPassword: String? = binding?.etConfirmPassword?.text?.toString()

        if (!viewModel.isEmailValid(email)) {
            binding?.tilEmail?.error = getString(R.string.invalid_email)
            isValid = false
        }

        if (!viewModel.isPasswordValid(password)) {
            binding?.tilPassword?.error = getString(R.string.invalid_password)
            isValid = false
        }

        if (!viewModel.isConfirmPasswordValid(password, confirmPassword)) {
            binding?.tilConfirmPassword?.error = getString(R.string.invalid_confirm_password)
            isValid = false
        }

        return isValid
    }

    private fun shouldShowProgressUI(isVisible: Boolean) {
        binding?.cpi?.shouldShowProgressUI(isVisible)
    }

    private fun showUIElements(isEnabled: Boolean) {
        enableViewElements(
            listOf(binding?.btSignup, binding?.loginPrompt), isEnabled
        )
    }

    private fun clearTextFieldFocus() {
        clearTextFieldFocus(
            listOf(binding?.tilEmail, binding?.tilPassword, binding?.tilConfirmPassword)
        )
    }

    private fun registerListeners() {
        binding?.etEmail?.addTextChangedListener(getWatcher(binding?.tilEmail, watcherHashSet))
        binding?.etPassword?.addTextChangedListener(
            getWatcher(binding?.tilPassword, watcherHashSet)
        )
        binding?.etConfirmPassword?.addTextChangedListener(
            getWatcher(binding?.tilConfirmPassword, watcherHashSet)
        )
    }

    private fun unregisterListeners() {
        watcherHashSet.forEach {
            binding?.etEmail?.removeTextChangedListener(it)
            binding?.etPassword?.removeTextChangedListener(it)
            binding?.etConfirmPassword?.removeTextChangedListener(it)
        }
        watcherHashSet.clear()
    }

    private fun navigateToScreen(navID: Int) {
        screensNavigator.navigateToScreen(navID, this)
    }
}