package com.arpansircar.notes_app.data.network

class AuthInvoker {
    private var errorMessage: String? = null

    suspend operator fun invoke(function: suspend () -> Unit): String? {
        setErrorMessageAsNull()
        try {
            function.invoke()
        } catch (e: Exception) {
            errorMessage = e.message
        }
        return errorMessage;
    }

    private fun setErrorMessageAsNull() {
        errorMessage = null
    }
}