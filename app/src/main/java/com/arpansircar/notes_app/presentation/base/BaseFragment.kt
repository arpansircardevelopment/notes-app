package com.arpansircar.notes_app.presentation.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.arpansircar.notes_app.di.container.ApplicationContainerRoot
import com.arpansircar.notes_app.di.container.AuthContainerRoot
import com.arpansircar.notes_app.di.container.HomeContainerRoot
import com.arpansircar.notes_app.di.injector.AuthInjector
import com.arpansircar.notes_app.di.injector.HomeInjector

open class BaseFragment : Fragment() {

    private val applicationContainerRoot: ApplicationContainerRoot by lazy {
        (requireActivity() as BaseActivity).appContainerRoot
    }

    private val authContainerRoot: AuthContainerRoot by lazy {
        AuthContainerRoot(applicationContainerRoot, this)
    }

    protected val authInjector: AuthInjector by lazy { AuthInjector(authContainerRoot) }

    private val homeContainerRoot: HomeContainerRoot by lazy {
        HomeContainerRoot(applicationContainerRoot, this)
    }

    protected val homeInjector: HomeInjector by lazy { HomeInjector(homeContainerRoot) }

    protected fun initializeBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    homeContainerRoot.screensNavigator.triggerActivityFinish()
                }
            })
    }
}