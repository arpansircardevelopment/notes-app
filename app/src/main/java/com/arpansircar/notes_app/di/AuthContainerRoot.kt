package com.arpansircar.notes_app.di

import androidx.fragment.app.Fragment
import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import com.arpansircar.notes_app.presentation.ScreensNavigator
import com.arpansircar.notes_app.presentation.viewmodel.factory.LoginViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.SignupViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.UserViewModelFactory

class AuthContainerRoot(
    private val applicationContainerRoot: ApplicationContainerRoot,
    private val fragment: Fragment
) {

    val screensNavigator: ScreensNavigator by lazy { ScreensNavigator(fragment) }

    private val authInvoker: AuthInvoker by lazy { applicationContainerRoot.authInvoker }

    private val firebaseContainerRoot get() = FirebaseContainerRoot()

    private val firebaseAuth get() = firebaseContainerRoot.firebaseAuth

    val currentUser get() = firebaseContainerRoot.currentUser

    val userDisplayName get() = currentUser?.displayName

    private val authRepository: AuthRepository get() = AuthRepository(firebaseAuth, authInvoker)

    val loginViewModelFactory: LoginViewModelFactory by lazy { LoginViewModelFactory(authRepository) }

    val signupViewModelFactory: SignupViewModelFactory by lazy {
        SignupViewModelFactory(authRepository)
    }

    val userViewModelFactory: UserViewModelFactory by lazy { UserViewModelFactory(authRepository) }

}