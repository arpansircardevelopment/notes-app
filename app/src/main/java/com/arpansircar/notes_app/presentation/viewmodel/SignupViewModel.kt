package com.arpansircar.notes_app.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _responseObserver: MutableLiveData<String?> = MutableLiveData()
    val responseObserver: LiveData<String?> = _responseObserver

    fun validateData(
        emailEditText: TextInputEditText?,
        passwordEditText: TextInputEditText?,
        confirmPasswordEditText: TextInputEditText?
    ): Boolean {
        if (emailEditText?.text?.isEmpty() == true) {
            emailEditText.error = "Field cannot be empty"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText?.text.toString()).matches()) {
            emailEditText?.error = "Not a valid email address"
            return false
        }

        if (passwordEditText?.text?.isEmpty() == true) {
            passwordEditText.error = "Field cannot be empty"
            return false
        }

        if (confirmPasswordEditText?.text?.isEmpty() == true) {
            confirmPasswordEditText.error = "Field cannot be empty"
            return false
        }

        if (passwordEditText?.text?.toString()
                ?.equals(confirmPasswordEditText?.text.toString()) == false
        ) {
            confirmPasswordEditText?.error = "Password and Confirm Password are not the same"
            return false
        }
        return true
    }

    fun createUser(emailEditText: TextInputEditText?, passwordEditText: TextInputEditText?) {
        var response: String?
        viewModelScope.launch(Dispatchers.IO) {
            response = authRepository.userSignup(
                emailEditText?.text?.toString() ?: "",
                passwordEditText?.text?.toString() ?: ""
            )
            _responseObserver.postValue(response)
        }
    }
}