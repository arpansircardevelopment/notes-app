package com.arpansircar.notes_app.di

import android.app.Application
import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.data.local.db.NotesDatabase
import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.presentation.ScreensNavigator

class ApplicationContainerRoot(application: Application) {

    private val notesDatabase: NotesDatabase by lazy { NotesDatabase.getInstance(application) }

    val notesDao: NotesDao by lazy { notesDatabase.notesDao() }

    val datastoreContainer: NotesDatastoreContainer by lazy { NotesDatastoreContainer(application) }

    val authInvoker: AuthInvoker by lazy { AuthInvoker() }

}