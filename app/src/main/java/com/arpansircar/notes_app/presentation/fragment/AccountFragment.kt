package com.arpansircar.notes_app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var binding: FragmentAccountBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding?.apply {
            avProfileImage.avatarInitials = "A"
            tvUserName.text = "Arpan Sircar"
            tvUserEmail.text = "arpansircar1@gmail.com"
        }

        return binding?.root
    }
}