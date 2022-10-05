package com.arpansircar.notes_app.di

import com.arpansircar.notes_app.data.local.NotesDao
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.arpansircar.notes_app.presentation.viewmodel.factory.AddEditNoteViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.HomeViewModelFactory

class HomeContainer(container: FirebaseContainer, notesDao: NotesDao) {

    private val homeRepository: HomeRepository = HomeRepository(notesDao, container)

    val homeViewModelFactory: HomeViewModelFactory = HomeViewModelFactory(homeRepository)

    val addEditNoteViewModelFactory: AddEditNoteViewModelFactory =
        AddEditNoteViewModelFactory(homeRepository)

}