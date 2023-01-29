package com.arpansircar.notes_app.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.domain.repositories.HomeRepository
import com.arpansircar.notes_app.presentation.utils.ScreensNavigator
import com.arpansircar.notes_app.presentation.viewmodel.*
import com.arpansircar.notes_app.presentation.viewmodel.factory.*

class HomeContainerRoot(
    private val applicationContainerRoot: ApplicationContainerRoot, private val fragment: Fragment
) {

    private val notesDao: NotesDao by lazy { applicationContainerRoot.notesDao }

    private val datastoreContainer: NotesDatastoreContainer by lazy { applicationContainerRoot.datastoreContainer }

    private val authInvoker get() = applicationContainerRoot.authInvoker

    private val firebaseContainerRoot get() = FirebaseContainerRoot()

    val dialogManager by lazy { applicationContainerRoot.dialogManager }

    val screensNavigator: ScreensNavigator by lazy { applicationContainerRoot.screensNavigator }

    val firebaseAuth get() = firebaseContainerRoot.firebaseAuth

    private val homeRepository: HomeRepository
        get() = HomeRepository(
            notesDao, firebaseContainerRoot, datastoreContainer, authInvoker, firebaseAuth
        )

    private val homeViewModelFactory: HomeViewModelFactory by lazy {
        HomeViewModelFactory(homeRepository)
    }

    private val addEditNoteViewModelFactory: AddEditNoteViewModelFactory by lazy {
        AddEditNoteViewModelFactory(homeRepository)
    }

    private val accountViewModelFactory: AccountViewModelFactory by lazy {
        AccountViewModelFactory(homeRepository)
    }

    private val userDetailViewModelFactory: UserDetailViewModelFactory by lazy {
        UserDetailViewModelFactory(homeRepository)
    }

    private val editUserDetailViewModelFactory: EditUserDetailViewModelFactory by lazy {
        EditUserDetailViewModelFactory(homeRepository)
    }

    val homeViewModel: HomeViewModel
        get() = ViewModelProvider(fragment, homeViewModelFactory)[HomeViewModel::class.java]

    val addEditNoteViewModel: AddEditNoteViewModel
        get() = ViewModelProvider(
            fragment, addEditNoteViewModelFactory
        )[AddEditNoteViewModel::class.java]

    val accountViewModel: AccountViewModel
        get() = ViewModelProvider(fragment, accountViewModelFactory)[AccountViewModel::class.java]

    val userDetailViewModel: UserDetailsViewModel
        get() = ViewModelProvider(
            fragment,
            userDetailViewModelFactory
        )[UserDetailsViewModel::class.java]

    val editUserDetailViewModel: EditUserDetailViewModel
        get() = ViewModelProvider(
            fragment, editUserDetailViewModelFactory
        )[EditUserDetailViewModel::class.java]
}