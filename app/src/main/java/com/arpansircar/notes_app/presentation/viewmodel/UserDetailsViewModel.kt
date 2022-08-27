package com.arpansircar.notes_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _responseObserver: MutableLiveData<String?> = MutableLiveData()
    val responseObserver: LiveData<String?> = _responseObserver

    fun validateData(nameEditText: TextInputEditText?): Boolean {
        if (nameEditText?.text?.toString()?.isEmpty() == true) {
            nameEditText.error = "This field cannot be empty"
            return false
        }
        return true
    }

    fun updateDetails(nameEditText: TextInputEditText?) {
        val name: String = nameEditText?.text?.toString()!!
        val changeRequest: UserProfileChangeRequest = UserProfileChangeRequest
            .Builder()
            .setDisplayName(name)
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            val response: String? = authRepository.updateUserProfile(changeRequest)
            _responseObserver.postValue(response)
        }
    }
}