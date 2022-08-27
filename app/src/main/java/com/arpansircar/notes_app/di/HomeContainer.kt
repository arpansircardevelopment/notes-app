package com.arpansircar.notes_app.di

import com.arpansircar.notes_app.data.local.NotesDao
import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.arpansircar.notes_app.presentation.viewmodel.factory.HomeViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class HomeContainer(val firebaseAuth: FirebaseAuth, notesDao: NotesDao) {

    private val authInvoker: AuthInvoker = AuthInvoker()

    private val homeRepository: HomeRepository = HomeRepository(firebaseAuth, authInvoker, notesDao)

    val homeViewModelFactory: HomeViewModelFactory = HomeViewModelFactory(homeRepository)

}