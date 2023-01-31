package com.arpansircar.notes_app.di.injector

import com.arpansircar.notes_app.di.container.AuthContainerRoot
import com.arpansircar.notes_app.presentation.fragment.LoginFragment
import com.arpansircar.notes_app.presentation.fragment.SignupFragment

class AuthInjector(private val containerRoot: AuthContainerRoot) {

    fun inject(fragment: LoginFragment) {
        fragment.currentUser = containerRoot.currentUser
        fragment.viewModel = containerRoot.loginViewModel
        fragment.screensNavigator = containerRoot.screensNavigator
        fragment.firebaseAuth = containerRoot.firebaseAuth
    }

    fun inject(fragment: SignupFragment) {
        fragment.currentUser = containerRoot.currentUser
        fragment.viewModel = containerRoot.signupViewModel
        fragment.screensNavigator = containerRoot.screensNavigator
    }

}