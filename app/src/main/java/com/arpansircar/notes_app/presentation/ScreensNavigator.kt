package com.arpansircar.notes_app.presentation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ScreensNavigator(private val fragment: Fragment) {
    fun navigateToScreen(destinationID: Int) {
        fragment.findNavController().navigate(destinationID)
    }

    fun triggerActivityFinish() {
        fragment.requireActivity().finish()
    }
}