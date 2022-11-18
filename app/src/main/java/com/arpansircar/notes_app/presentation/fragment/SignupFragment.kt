package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.FragmentSignupBinding
import com.arpansircar.notes_app.di.AuthContainer
import com.arpansircar.notes_app.presentation.viewmodel.SignupViewModel
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment() {

    private var authContainer: AuthContainer? = null

    private var binding: FragmentSignupBinding? = null
    private lateinit var viewModel: SignupViewModel

    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authContainer = AuthContainer()
        viewModel = ViewModelProvider(
            this, authContainer?.signupViewModelFactory!!
        )[SignupViewModel::class.java]
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
            findNavController().navigate(R.id.action_signup_to_login)
        }

        setTextFieldListener()

        viewModel.responseObserver.observe(viewLifecycleOwner) {
            shouldShowProgressUI(false)
            showUIElements(true)

            if (it == null) {
                Toast.makeText(
                    requireContext(), getString(R.string.account_created), Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.action_signup_to_user_details)
                return@observe
            }
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        authContainer = null
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
        binding?.cpi?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showUIElements(isEnabled: Boolean) {
        binding?.btSignup?.isEnabled = isEnabled
        binding?.loginPrompt?.isEnabled = isEnabled
    }

    private fun clearTextFieldFocus() {
        binding?.tilEmail?.clearFocus()
        binding?.tilPassword?.clearFocus()
    }

    private fun setTextFieldListener() {
        binding?.etEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding?.tilEmail?.apply {
                    removeErrorFromTextInputLayout(binding?.tilEmail)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding?.etPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                removeErrorFromTextInputLayout(binding?.tilPassword)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding?.etConfirmPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                removeErrorFromTextInputLayout(binding?.tilConfirmPassword)
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun removeErrorFromTextInputLayout(layout: TextInputLayout?) {
        layout?.apply {
            error = null
            isErrorEnabled = false
        }
    }
}