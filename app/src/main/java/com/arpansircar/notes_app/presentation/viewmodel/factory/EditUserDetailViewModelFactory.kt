package com.arpansircar.notes_app.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.arpansircar.notes_app.presentation.viewmodel.EditUserDetailViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class EditUserDetailViewModelFactory(
    private val repository: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditUserDetailViewModel::class.java)) {
            return EditUserDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Class Provided")
    }
}