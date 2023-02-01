package com.arpansircar.notes_app.di.injector

import com.arpansircar.notes_app.di.container.HomeContainerRoot
import com.arpansircar.notes_app.presentation.fragment.*

class HomeInjector(private val containerRoot: HomeContainerRoot) {

    fun inject(fragment: HomeFragment) {
        fragment.viewModel = containerRoot.homeViewModel
        fragment.dialogManager = containerRoot.dialogManager
        fragment.screensNavigator = containerRoot.screensNavigator
    }

    fun inject(fragment: EditDetailsListFragment) {
        fragment.screensNavigator = containerRoot.screensNavigator
    }

    fun inject(fragment: AccountFragment) {
        fragment.viewModel = containerRoot.accountViewModel
        fragment.firebaseAuth = containerRoot.firebaseAuth
        fragment.screensNavigator = containerRoot.screensNavigator
    }

    fun inject(fragment: AddEditNoteFragment) {
        fragment.viewModel = containerRoot.addEditNoteViewModel
        fragment.screensNavigator = containerRoot.screensNavigator
    }

    fun inject(fragment: EditUserDetailFragment) {
        fragment.viewModel = containerRoot.editUserDetailViewModel
    }

    fun inject(fragment: UserDetailsFragment) {
        fragment.viewModel = containerRoot.userDetailViewModel
    }
}