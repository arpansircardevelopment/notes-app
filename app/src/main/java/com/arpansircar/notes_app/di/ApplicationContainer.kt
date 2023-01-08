package com.arpansircar.notes_app.di

import android.app.Application
import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.data.local.db.NotesDatabase
import com.arpansircar.notes_app.data.network.AuthInvoker

class ApplicationContainer(application: Application) {

    private val notesDatabase: NotesDatabase = NotesDatabase.getInstance(application)

    val notesDao: NotesDao = notesDatabase.notesDao()

    val datastoreContainer: NotesDatastoreContainer = NotesDatastoreContainer(application)

    val authInvoker: AuthInvoker = AuthInvoker()

}