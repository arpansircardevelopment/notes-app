package com.arpansircar.notes_app.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpansircar.notes_app.common.ConstantsBase
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditUserDetailViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _responseObserver: MutableLiveData<String?> = MutableLiveData()
    val responseObserver: LiveData<String?> = _responseObserver

    fun validateData(data: String?, changeType: String): Boolean {
        var isValid = false

        when (changeType) {
            ConstantsBase.EDIT_DISPLAY_NAME -> isValid = isValidEmail(data)

            ConstantsBase.EDIT_EMAIL_ADDRESS -> isValid = isValidPassword(data) == true

            ConstantsBase.EDIT_PASSWORD -> isValid = isValidDisplayName(data) == true
        }

        return isValid
    }

    fun updateDetails(data: String, changeType: String) {
        when (changeType) {
            ConstantsBase.EDIT_DISPLAY_NAME -> updateDisplayName(data)

            ConstantsBase.EDIT_EMAIL_ADDRESS -> {}

            ConstantsBase.EDIT_PASSWORD -> {}
        }
    }

    private fun updateDisplayName(data: String) {
        val changeRequestBuilder = UserProfileChangeRequest.Builder()
        val changeRequest = changeRequestBuilder.setDisplayName(data).build()

        viewModelScope.launch(Dispatchers.IO) {
            val response: String? = homeRepository.updateUserProfile(changeRequest)
            _responseObserver.postValue(response)
        }
    }

    private fun isValidEmail(email: String?): Boolean =
        email?.isNotEmpty() == true && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String?) = password?.isNotEmpty()

    private fun isValidDisplayName(displayName: String?) = displayName?.isNotEmpty()
}