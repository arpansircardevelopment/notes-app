package com.arpansircar.notes_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    fun fetchCurrentUserDetails(): FirebaseUser? = homeRepository.getCurrentUserDetails()
    fun fetchFirebaseAuth(): FirebaseAuth = homeRepository.getFirebaseAuth()
}