package com.arpansircar.notes_app.di.container

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arpansircar.notes_app.data.network.AuthInvoker
import com.arpansircar.notes_app.domain.repositories.AuthRepository
import com.arpansircar.notes_app.presentation.utils.ScreensNavigator
import com.arpansircar.notes_app.presentation.viewmodel.LoginViewModel
import com.arpansircar.notes_app.presentation.viewmodel.SignupViewModel
import com.arpansircar.notes_app.presentation.viewmodel.factory.LoginViewModelFactory
import com.arpansircar.notes_app.presentation.viewmodel.factory.SignupViewModelFactory

class AuthContainerRoot(
    private val applicationContainerRoot: ApplicationContainerRoot,
    private val fragment: Fragment
) {
    private val authInvoker: AuthInvoker by lazy { applicationContainerRoot.authInvoker }

    private val firebaseContainerRoot get() = FirebaseContainerRoot()

    val firebaseAuth get() = firebaseContainerRoot.firebaseAuth

    private val authRepository: AuthRepository by lazy { AuthRepository(firebaseAuth, authInvoker) }

    private val loginViewModelFactory: LoginViewModelFactory by lazy {
        LoginViewModelFactory(authRepository)
    }

    private val signupViewModelFactory: SignupViewModelFactory by lazy {
        SignupViewModelFactory(authRepository)
    }

    val screensNavigator: ScreensNavigator by lazy { ScreensNavigator(fragment) }

    val currentUser get() = firebaseContainerRoot.currentUser

    val loginViewModel: LoginViewModel
        get() = ViewModelProvider(fragment, loginViewModelFactory)[LoginViewModel::class.java]

    val signupViewModel: SignupViewModel
        get() = ViewModelProvider(fragment, signupViewModelFactory)[SignupViewModel::class.java]
}