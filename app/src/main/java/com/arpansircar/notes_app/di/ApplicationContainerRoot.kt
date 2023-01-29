package com.arpansircar.notes_app.di

import android.app.Application
import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.data.local.db.NotesDatabase
import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.presentation.ScreensNavigator
import com.arpansircar.notes_app.presentation.utils.DialogManager

class ApplicationContainerRoot(application: Application) {

    private val notesDatabase: NotesDatabase by lazy { NotesDatabase.getInstance(application) }

    val notesDao: NotesDao by lazy { notesDatabase.notesDao() }

    val datastoreContainer: NotesDatastoreContainer by lazy { NotesDatastoreContainer(application) }

    val authInvoker: AuthInvoker by lazy { AuthInvoker() }

    val screensNavigator: ScreensNavigator by lazy { ScreensNavigator() }

    val dialogManager by lazy { DialogManager() }

}