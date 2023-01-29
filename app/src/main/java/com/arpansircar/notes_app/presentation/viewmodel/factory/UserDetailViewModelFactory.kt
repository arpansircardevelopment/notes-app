package com.arpansircar.notes_app.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.arpansircar.notes_app.presentation.viewmodel.UserDetailsViewModel

@Suppress("UNCHECKED_CAST")
class UserDetailViewModelFactory(private val repository: HomeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            return UserDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Class Provided")
    }
}