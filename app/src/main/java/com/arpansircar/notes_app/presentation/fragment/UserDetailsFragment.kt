package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.ManualDiPracticeApplication
import com.arpansircar.notes_app.databinding.FragmentUserDetailsBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.arpansircar.notes_app.di.AuthContainer
import com.arpansircar.notes_app.presentation.viewmodel.UserDetailsViewModel

class UserDetailsFragment : Fragment() {

    private var appContainer: ApplicationContainer? = null
    private var authContainer: AuthContainer? = null

    private var binding: FragmentUserDetailsBinding? = null
    private lateinit var viewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as ManualDiPracticeApplication).appContainer
        authContainer = appContainer?.authContainer
        viewModel = ViewModelProvider(
            this,
            authContainer?.userViewModelFactory!!
        )[UserDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.responseObserver.observe(viewLifecycleOwner) {
            binding?.cpi?.visibility = View.GONE

            if (it == null) {
                findNavController().navigate(R.id.action_user_details_to_home)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.btUpdateDetails?.setOnClickListener {
            binding?.cpi?.visibility = View.VISIBLE
            binding?.tilName?.clearFocus()

            if (viewModel.validateData(binding?.etName)) {
                viewModel.updateDetails(binding?.etName)
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