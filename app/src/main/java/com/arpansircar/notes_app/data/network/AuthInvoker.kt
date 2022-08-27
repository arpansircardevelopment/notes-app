package com.arpansircar.notes_app.data.network

import com.arpansircar.notes_app.common.ConstantsBase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class AuthInvoker {
    private var errorMessage: String? = null

    suspend operator fun invoke(function: suspend () -> Unit): String? {
        setErrorMessageAsNull()
        try {
            function.invoke()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            errorMessage = ConstantsBase.INCORRECT_PASSWORD_ERROR
        } catch (e: FirebaseAuthInvalidUserException) {
            errorMessage = ConstantsBase.USER_DOES_NOT_EXIST_ERROR
        } catch (e: FirebaseNetworkException) {
            errorMessage = ConstantsBase.INTERNET_CONNECTIVITY_ISSUES_ERROR
        } catch (e: FirebaseAuthUserCollisionException) {
            errorMessage = ConstantsBase.USER_ALREADY_EXISTS_ERROR
        } catch (e: Exception) {
            errorMessage = e.message
        }
        return errorMessage;
    }

    private fun setErrorMessageAsNull() {
        errorMessage = null
    }
}