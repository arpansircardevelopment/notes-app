package com.arpansircar.notes_app.common

import android.app.Application
import com.arpansircar.notes_app.di.ApplicationContainerRoot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotesApplication : Application() {

    lateinit var appContainer: ApplicationContainerRoot

    override fun onCreate() {
        super.onCreate()
        appContainer = ApplicationContainerRoot(this)
        Firebase.database.setPersistenceEnabled(true)
    }
}