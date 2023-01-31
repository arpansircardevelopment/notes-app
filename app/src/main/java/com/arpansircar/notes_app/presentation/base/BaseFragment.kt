package com.arpansircar.notes_app.presentation.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.arpansircar.notes_app.di.container.ApplicationContainerRoot
import com.arpansircar.notes_app.di.container.AuthContainerRoot
import com.arpansircar.notes_app.di.container.HomeContainerRoot
import com.arpansircar.notes_app.di.injector.AuthInjector

open class BaseFragment : Fragment() {

    private val applicationContainerRoot: ApplicationContainerRoot by lazy {
        (requireActivity() as BaseActivity).appContainerRoot
    }

    private val authContainerRoot: AuthContainerRoot by lazy {
        AuthContainerRoot(applicationContainerRoot, this)
    }

    protected val authInjector: AuthInjector by lazy { AuthInjector(authContainerRoot) }

    protected val homeContainerRoot: HomeContainerRoot by lazy {
        HomeContainerRoot(applicationContainerRoot, this)
    }

    protected fun initializeBackPressedDispatcher(fragmentContext: Fragment) {
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    homeContainerRoot.screensNavigator.triggerActivityFinish(fragmentContext)
                }
            })
    }
}