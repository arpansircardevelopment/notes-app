package com.arpansircar.notes_app.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import com.arpansircar.notes_app.presentation.viewmodel.SignupViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SignupViewModelFactory(private val authRepository: AuthRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown Class Provided")
    }
}