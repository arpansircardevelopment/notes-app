package com.arpansircar.notes_app.domain.repositories

import com.arpansircar.notes_app.data.network.AuthInvoker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val authInvoker: AuthInvoker
) {

    suspend fun userLogin(email: String, password: String): String? {
        return authInvoker.invoke {
            firebaseAuth.signInWithEmailAndPassword(
                email,
                password
            ).await()
        }
    }

    suspend fun userSignup(email: String, password: String): String? {
        return authInvoker.invoke {
            firebaseAuth.createUserWithEmailAndPassword(
                email,
                password
            ).await()
        }
    }
}