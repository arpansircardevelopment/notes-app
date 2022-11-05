package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.FragmentEditUserDetailBinding

class EditUserDetailFragment : Fragment() {

    private var binding: FragmentEditUserDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUserDetailBinding.inflate(inflater, container, false)
        setUIData()
        return binding?.root
    }

    private fun setUIData(string: String = "Username") {
        binding?.apply {
            tvHeader.text = getString(R.string.enter_new_detail_message, string)
            etUserDetail.hint = string
            btUpdateDetails.text = getString(R.string.update_custom_details, string)
        }
    }
}