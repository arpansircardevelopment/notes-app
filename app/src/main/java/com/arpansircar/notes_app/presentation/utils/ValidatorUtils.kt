package com.arpansircar.notes_app.presentation.utils

import android.util.Patterns

object ValidatorUtils {

    // Validate Email fields entered by the user throughout the app
    fun String?.isEmailValid(): Boolean {
        if (this.isNullOrEmpty() || this.isBlank()) {
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
            return false
        }
        return true
    }

    // Validate Password fields entered by the user throughout the app
    fun String?.isPasswordValid(): Boolean {
        if (this.isNullOrEmpty() || this.isBlank()) {
            return false
        }
        return true
    }

    // Validate Confirm Password fields throughout the app
    fun String?.isConfirmPasswordValid(password: String?): Boolean {
        if (this.isNullOrEmpty() || this.isBlank()) {
            return false
        }
        if (password != this) {
            return false
        }
        return true
    }
}