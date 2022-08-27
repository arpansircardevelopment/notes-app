package com.arpansircar.notes_app.di

import android.app.Application
import com.arpansircar.notes_app.data.local.NotesDao
import com.arpansircar.notes_app.data.local.NotesDatabase
import com.google.firebase.auth.FirebaseAuth

class ApplicationContainer(application: Application) {

    private val notesDatabase: NotesDatabase = NotesDatabase.getInstance(application)

    private val notesDao: NotesDao = notesDatabase.notesDao()

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val authContainer: AuthContainer by lazy { AuthContainer(firebaseAuth) }

    val homeContainer: HomeContainer by lazy { HomeContainer(firebaseAuth, notesDao) }

}