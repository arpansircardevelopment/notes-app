package com.arpansircar.notes_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import com.arpansircar.notes_app.presentation.utils.ValidatorUtils.isConfirmPasswordValid
import com.arpansircar.notes_app.presentation.utils.ValidatorUtils.isEmailValid
import com.arpansircar.notes_app.presentation.utils.ValidatorUtils.isPasswordValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _responseObserver: MutableLiveData<String?> = MutableLiveData()
    val responseObserver: LiveData<String?> = _responseObserver

    fun isEmailValid(email: String?): Boolean = email.isEmailValid()

    fun isPasswordValid(password: String?): Boolean = password.isPasswordValid()

    fun isConfirmPasswordValid(password: String?, confirmPassword: String?): Boolean =
        confirmPassword.isConfirmPasswordValid(password)

    fun createUser(email: String, password: String) {
        var response: String?
        viewModelScope.launch(Dispatchers.IO) {
            response = authRepository.userSignup(email, password)
            _responseObserver.postValue(response)
        }
    }
}