package com.arpansircar.notes_app.di

import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import com.arpansircar.notes_app.presentation.viewmodel.factory.LoginViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.SignupViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.UserViewModelFactory

class AuthContainer(authInvoker: AuthInvoker) {

    private val firebaseContainer = FirebaseContainer()

    val firebaseAuth = firebaseContainer.firebaseAuth

    val currentUser = firebaseContainer.currentUser

    private val authRepository: AuthRepository = AuthRepository(
        firebaseAuth,
        authInvoker
    )

    val loginViewModelFactory: LoginViewModelFactory = LoginViewModelFactory(authRepository)

    val signupViewModelFactory: SignupViewModelFactory = SignupViewModelFactory(authRepository)

    val userViewModelFactory: UserViewModelFactory = UserViewModelFactory(authRepository)

}