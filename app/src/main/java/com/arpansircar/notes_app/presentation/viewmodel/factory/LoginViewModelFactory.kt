package com.arpansircar.notes_app.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import com.arpansircar.notes_app.presentation.viewmodel.LoginViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown Class Provided")
    }
}