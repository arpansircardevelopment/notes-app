package com.arpansircar.notes_app.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _responseObserver: MutableLiveData<String?> = MutableLiveData()
    val responseObserver: LiveData<String?> = _responseObserver

    fun userLogin(email: String, password: String) {
        var response: String?
        viewModelScope.launch(Dispatchers.IO) {
            response = authRepository.userLogin(email, password)
            _responseObserver.postValue(response)
        }
    }

    fun isEmailValid(email: String?): Boolean {
        if (email.isNullOrEmpty() || email.isBlank()) {
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        return true
    }

    fun isPasswordValid(password: String?): Boolean {
        if (password.isNullOrEmpty() || password.isBlank()) {
            return false
        }
        return true
    }
}