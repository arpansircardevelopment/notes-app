package com.arpansircar.notes_app.di

import android.app.Application
import com.arpansircar.notes_app.data.local.datastore.NotesDatastoreContainer
import com.arpansircar.notes_app.data.local.db.NotesDao
import com.arpansircar.notes_app.data.local.db.NotesDatabase

class ApplicationContainer(application: Application) {

    private val notesDatabase: NotesDatabase = NotesDatabase.getInstance(application)

    private val notesDao: NotesDao = notesDatabase.notesDao()

    private val firebaseContainer = FirebaseContainer()

    private val datastoreContainer: NotesDatastoreContainer = NotesDatastoreContainer(application)

    val firebaseAuth by lazy { firebaseContainer.firebaseAuth }

    val authContainer: AuthContainer by lazy { AuthContainer(firebaseContainer.firebaseAuth) }

    val homeContainer: HomeContainer by lazy {
        HomeContainer(
            firebaseContainer,
            notesDao,
            datastoreContainer
        )
    }
}