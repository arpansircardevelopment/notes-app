package com.arpansircar.notes_app.di

import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.arpansircar.notes_app.presentation.viewmodel.AccountViewModel
import com.arpansircar.notes_app.presentation.viewmodel.factory.AccountViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.AddEditNoteViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.HomeViewModelFactory

class HomeContainer(
    container: FirebaseContainer,
    notesDao: NotesDao,
    datastoreContainer: NotesDatastoreContainer
) {

    private val homeRepository: HomeRepository =
        HomeRepository(notesDao, container, datastoreContainer)

    val homeViewModelFactory: HomeViewModelFactory = HomeViewModelFactory(homeRepository)

    val addEditNoteViewModelFactory: AddEditNoteViewModelFactory =
        AddEditNoteViewModelFactory(homeRepository)

    val accountViewModelFactory: AccountViewModelFactory = AccountViewModelFactory(homeRepository)

}