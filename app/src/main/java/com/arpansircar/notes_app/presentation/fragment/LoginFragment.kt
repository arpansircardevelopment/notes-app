package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentLoginBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.arpansircar.notes_app.di.AuthContainer
import com.arpansircar.notes_app.presentation.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private var appContainer: ApplicationContainer? = null
    private var authContainer: AuthContainer? = null

    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as NotesApplication).appContainer
        authContainer = appContainer?.authContainer
        viewModel = ViewModelProvider(
            this,
            authContainer?.loginViewModelFactory!!
        )[LoginViewModel::class.java]
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
            findNavController().navigate(R.id.action_login_to_signup)
        }

        viewModel.responseObserver.observe(viewLifecycleOwner) {
            binding?.cpi?.visibility = View.GONE

            if (it == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.logged_in),
                    Toast.LENGTH_SHORT
                ).show()

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

    override fun onResume() {
        super.onResume()
        binding?.btLogin?.setOnClickListener {

            binding?.cpi?.visibility = View.VISIBLE
            binding?.tilEmail?.clearFocus()
            binding?.tilPassword?.clearFocus()

            if (viewModel.validateData(binding?.etEmail, binding?.etPassword)) {
                viewModel.userLogin(binding?.etEmail, binding?.etPassword)
            } else {
                binding?.cpi?.visibility = View.GONE
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
        appContainer = null
    }
}