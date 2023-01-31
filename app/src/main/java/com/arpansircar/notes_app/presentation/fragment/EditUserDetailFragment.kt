package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.ConstantsBase
import com.arpansircar.notes_app.databinding.FragmentEditUserDetailBinding
import com.arpansircar.notes_app.presentation.base.BaseFragment
import com.arpansircar.notes_app.presentation.viewmodel.EditUserDetailViewModel

class EditUserDetailFragment : BaseFragment() {

    private var detailType: String? = null
    private var binding: FragmentEditUserDetailBinding? = null
    private lateinit var viewModel: EditUserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = homeContainerRoot.editUserDetailViewModel
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