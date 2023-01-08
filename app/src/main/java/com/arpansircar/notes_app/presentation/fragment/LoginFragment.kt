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
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentLoginBinding
import com.arpansircar.notes_app.di.AuthContainer
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.clearTextFieldFocus
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.enableViewElements
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.removeErrorMessage
import com.arpansircar.notes_app.presentation.utils.DisplayUtils.shouldShowProgressUI
import com.arpansircar.notes_app.presentation.utils.ListenerUtils.getWatcher
import com.arpansircar.notes_app.presentation.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var authContainer: AuthContainer? = null

    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel
    private val watcherHashSet = HashSet<TextWatcher>()

    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authInvoker =
            (activity?.applicationContext as NotesApplication).appContainer.authInvoker

        authContainer = AuthContainer(authInvoker)

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

        viewModel.responseObserver.observe(viewLifecycleOwner) {
            shouldShowProgressUI(false)
            showUIElements(true)

            if (it == null) {
                Toast.makeText(requireContext(), getString(R.string.logged_in), Toast.LENGTH_SHORT)
                    .show()

                if (authContainer?.firebaseAuth?.currentUser?.displayName == null) {
                    findNavController().navigate(R.id.action_login_to_user_details)
                } else {
                    findNavController().navigate(R.id.action_login_to_home)
                }
                return@observe
            }

            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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

    override fun onDestroy() {
        super.onDestroy()
        authContainer = null
    }

    private fun initializeNavigation() {
        if (authContainer?.currentUser != null) {
            if (authContainer?.currentUser?.displayName == null || authContainer?.currentUser?.displayName?.isEmpty() == true) {
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
        binding?.etEmail?.addTextChangedListener(getWatcher(binding?.tilEmail, watcherHashSet))
        binding?.etPassword?.addTextChangedListener(
            getWatcher(binding?.tilPassword, watcherHashSet)
        )
    }

    private fun unregisterListener() {
        watcherHashSet.forEach {
            binding?.etEmail?.removeTextChangedListener(it)
            binding?.etPassword?.removeTextChangedListener(it)
        }
        watcherHashSet.clear()
    }
}