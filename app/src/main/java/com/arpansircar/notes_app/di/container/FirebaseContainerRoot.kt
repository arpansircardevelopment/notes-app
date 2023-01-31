package com.arpansircar.notes_app.di.container

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseContainerRoot {
    private val firebase: Firebase get() = Firebase

    val realtimeDb get() = firebase.database.reference

    val firebaseAuth: FirebaseAuth get() = FirebaseAuth.getInstance()

    val currentUser: FirebaseUser? get() = firebaseAuth.currentUser
}