package com.arpansircar.notes_app.di

import android.app.Application
import com.arpansircar.notes_app.data.local.NotesDao
import com.arpansircar.notes_app.data.local.NotesDatabase

class ApplicationContainer(application: Application) {

    private val notesDatabase: NotesDatabase = NotesDatabase.getInstance(application)

    private val notesDao: NotesDao = notesDatabase.notesDao()

    private val firebaseContainer = FirebaseContainer()

    val firebaseAuth by lazy { firebaseContainer.firebaseAuth }

    val authContainer: AuthContainer by lazy { AuthContainer(firebaseContainer.firebaseAuth) }

    val homeContainer: HomeContainer by lazy { HomeContainer(firebaseContainer, notesDao) }

}