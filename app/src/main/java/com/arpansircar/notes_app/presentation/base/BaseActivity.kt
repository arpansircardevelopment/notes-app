package com.arpansircar.notes_app.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.di.container.ApplicationContainerRoot

open class BaseActivity : AppCompatActivity() {
    val appContainerRoot: ApplicationContainerRoot get() = (application as NotesApplication).appContainer
}