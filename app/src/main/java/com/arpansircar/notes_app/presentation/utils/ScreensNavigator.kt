package com.arpansircar.notes_app.presentation.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ScreensNavigator(private val fragment: Fragment) {
    fun navigateToScreen(destinationID: Int) {
        fragment.findNavController().navigate(destinationID)
    }

    fun navigateWithBundle(destinationID: Int, bundle: Bundle) {
        fragment.findNavController().navigate(destinationID, bundle)
    }

    fun triggerActivityFinish() {
        fragment.requireActivity().finish()
    }

    fun navigateUp() {
        fragment.findNavController().navigateUp()
    }
}