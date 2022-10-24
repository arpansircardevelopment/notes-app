package com.arpansircar.notes_app.di

import android.app.Application
import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.data.local.db.NotesDatabase

class ApplicationContainer(application: Application) {

    private val notesDatabase: NotesDatabase = NotesDatabase.getInstance(application)

    val notesDao: NotesDao = notesDatabase.notesDao()

    val datastoreContainer: NotesDatastoreContainer = NotesDatastoreContainer(application)

}