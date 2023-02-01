package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.ConstantsBase
import com.arpansircar.notes_app.databinding.FragmentEditDetailsListBinding
import com.arpansircar.notes_app.presentation.base.BaseFragment
import com.arpansircar.notes_app.presentation.utils.ScreensNavigator

class EditDetailsListFragment : BaseFragment() {

    lateinit var screensNavigator: ScreensNavigator

    private var binding: FragmentEditDetailsListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeInjector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditDetailsListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()

        binding?.rlEditDisplayName?.setOnClickListener {
            val bundle = bundleOf(ConstantsBase.EDIT_TYPE to ConstantsBase.EDIT_DISPLAY_NAME)
            navigateToEditUserDetails(bundle)
        }

        binding?.rlEditPassword?.setOnClickListener {
            val bundle = bundleOf(ConstantsBase.EDIT_TYPE to ConstantsBase.EDIT_PASSWORD)
            navigateToEditUserDetails(bundle)
        }

        binding?.rlEditEmailAddress?.setOnClickListener {
            val bundle = bundleOf(ConstantsBase.EDIT_TYPE to ConstantsBase.EDIT_EMAIL_ADDRESS)
            navigateToEditUserDetails(bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun navigateToEditUserDetails(bundle: Bundle) {
        screensNavigator.navigateWithBundle(
            R.id.action_edit_details_list_to_edit_user_detail,
            bundle
        )
    }
}