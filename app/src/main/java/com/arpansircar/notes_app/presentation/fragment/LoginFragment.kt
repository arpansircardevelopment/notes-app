package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.FragmentLoginBinding
import com.arpansircar.notes_app.di.AuthContainer
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.clearTextFieldFocus
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.enableViewElements
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.removeErrorMessage
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.shouldShowProgressUI
import com.arpansircar.notes_app.presentation.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var authContainer: AuthContainer? = null

    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel

    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authContainer = AuthContainer()

        initializeNavigation()

        initializeBackPressedDispatcher()

        viewModel = ViewModelProvider(
            this, authContainer?.loginViewModelFactory!!
        )[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.signUpPrompt?.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signup)
        }

        setTextFieldListener()

        viewModel.responseObserver.observe(viewLifecycleOwner) {
            shouldShowProgressUI(false)
            showUIElements(true)

            if (it == null) {
                Toast.makeText(requireContext(), getString(R.string.logged_in), Toast.LENGTH_SHORT)
                    .show()

                if (authContainer?.firebaseContainer?.firebaseAuth?.currentUser?.displayName == null) {
                    findNavController().navigate(R.id.action_login_to_user_details)
                } else {
                    findNavController().navigate(R.id.action_login_to_home)
                }
                return@observe
            }

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        authContainer = null
    }

    private fun initializeNavigation() {
        if (authContainer?.firebaseContainer?.currentUser != null) {
            if (authContainer?.firebaseContainer?.currentUser?.displayName == null || authContainer?.firebaseContainer?.currentUser?.displayName?.isEmpty() == true) {
                findNavController().navigate(R.id.fragment_user_details)
            } else {
                findNavController().navigate(R.id.fragment_home)
            }
        }
    }

    private fun initializeBackPressedDispatcher() =
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

    private fun setTextFieldListener() {
        binding?.etEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding?.tilEmail?.removeErrorMessage()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding?.etPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding?.tilPassword?.removeErrorMessage()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
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
}