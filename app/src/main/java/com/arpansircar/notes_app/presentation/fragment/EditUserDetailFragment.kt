package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.ConstantsBase
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.FragmentEditUserDetailBinding
import com.arpansircar.notes_app.di.ApplicationContainerRoot
import com.arpansircar.notes_app.di.HomeContainerRoot
import com.arpansircar.notes_app.presentation.viewmodel.EditUserDetailViewModel

class EditUserDetailFragment : Fragment() {

    private var appContainer: ApplicationContainerRoot? = null
    private var homeContainerRoot: HomeContainerRoot? = null

    private var detailType: String? = null
    private var binding: FragmentEditUserDetailBinding? = null
    private lateinit var viewModel: EditUserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (requireActivity().application as NotesApplication).appContainer
        homeContainerRoot = HomeContainerRoot(appContainer?.notesDao!!, appContainer?.datastoreContainer!!)

        viewModel = ViewModelProvider(
            this, homeContainerRoot?.editUserDetailViewModelFactory!!
        )[EditUserDetailViewModel::class.java]

        detailType = arguments?.getString(ConstantsBase.EDIT_TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUserDetailBinding.inflate(inflater, container, false)
        setUIData(detailType)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        binding?.btUpdateDetails?.setOnClickListener {
            setProgressUI(true)

            val isValid: Boolean = viewModel.validateData(
                binding?.etUserDetail?.text?.toString(), changeType = detailType!!
            )

            if (isValid) {
                viewModel.updateDetails(detailType!!, binding?.etUserDetail?.text?.toString()!!)
            } else {
                Toast.makeText(
                    context, getString(R.string.invalid_details), Toast.LENGTH_LONG
                ).show()
            }

            setProgressUI(false)
        }
    }

    private fun setUIData(string: String?) {
        binding?.apply {
            tvHeader.text = getString(R.string.enter_new_detail_message, string)

            tilUserDetail.hint = capitalizeStrings(string)

            btUpdateDetails.text = getString(R.string.update_custom_details, string)
        }
    }

    private fun capitalizeStrings(string: String?): String? =
        string?.split(" ")?.joinToString(" ") {
            it.replaceFirstChar { word -> word.titlecase() }
        }?.trim()

    private fun setProgressUI(isInProgress: Boolean) {
        binding?.btUpdateDetails?.isEnabled = !isInProgress
        binding?.btUpdateDetails?.isVisible = isInProgress
    }
}