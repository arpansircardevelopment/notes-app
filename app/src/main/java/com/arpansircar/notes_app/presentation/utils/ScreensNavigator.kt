package com.arpansircar.notes_app.presentation.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ScreensNavigator {
    fun navigateToScreen(destinationID: Int, fragment: Fragment) {
        fragment.findNavController().navigate(destinationID)
    }

    fun navigateWithBundle(destinationID: Int, bundle: Bundle, fragment: Fragment) {
        fragment.findNavController().navigate(destinationID, bundle)
    }

    fun triggerActivityFinish(fragment: Fragment) {
        fragment.requireActivity().finish()
    }
}