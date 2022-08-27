package com.arpansircar.notes_app.common

import android.app.Application
import com.arpansircar.notes_app.di.ApplicationContainer

class ManualDiPracticeApplication : Application() {

    lateinit var appContainer: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = ApplicationContainer(this)
    }
}