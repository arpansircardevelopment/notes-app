package com.arpansircar.notes_app.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseContainer {
    private val firebase: Firebase = Firebase.also {
        it.database.setPersistenceEnabled(true)
    }

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val realtimeDb = firebase.database.reference
}