package com.arpansircar.notes_app.common

import android.app.Application
import com.arpansircar.notes_app.di.ApplicationContainer
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotesApplication : Application() {

    lateinit var appContainer: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = ApplicationContainer(this)
        Firebase.database.setPersistenceEnabled(true)
    }
}