package com.arpansircar.notes_app.di

import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.arpansircar.notes_app.presentation.viewmodel.factory.AccountViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.AddEditNoteViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.EditUserDetailViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.HomeViewModelFactory

class HomeContainer(
    notesDao: NotesDao, datastoreContainer: NotesDatastoreContainer
) {

    val firebaseContainer = FirebaseContainer()

    private val authInvoker = AuthInvoker()

    private val homeRepository: HomeRepository =
        HomeRepository(
            notesDao,
            firebaseContainer,
            datastoreContainer,
            authInvoker,
            firebaseContainer.firebaseAuth
        )

    val homeViewModelFactory: HomeViewModelFactory = HomeViewModelFactory(homeRepository)

    val addEditNoteViewModelFactory: AddEditNoteViewModelFactory =
        AddEditNoteViewModelFactory(homeRepository)

    val accountViewModelFactory: AccountViewModelFactory = AccountViewModelFactory(homeRepository)

    val editUserDetailViewModelFactory: EditUserDetailViewModelFactory =
        EditUserDetailViewModelFactory(homeRepository)
}