package com.arpansircar.notes_app.presentation.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputLayout

object DisplayUtils {

    // Method to remove error messages from TextInputLayouts throughout the app
    fun TextInputLayout.removeErrorMessage() {
        this.error = null
        this.isErrorEnabled = false
    }

    // Method to remove focus from all the TextInputLayouts passed in the list parameter
    fun clearTextFieldFocus(textFieldList: List<TextInputLayout?>) {
        textFieldList.forEach { it?.clearFocus() }
    }

    // Method to show or hide circular progress indicator based on the parameter value
    fun CircularProgressIndicator.shouldShowProgressUI(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    // Method to enable or disable UI elements present in the list
    fun enableViewElements(viewList: List<View?>, isEnabled: Boolean) {
        viewList.forEach { it?.isEnabled = isEnabled }
    }

    fun Fragment.showShortToast(string: String) {
        Toast.makeText(this.requireContext(), string, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.showLongToast(string: String) {
        Toast.makeText(this.requireContext(), string, Toast.LENGTH_LONG).show()
    }
}