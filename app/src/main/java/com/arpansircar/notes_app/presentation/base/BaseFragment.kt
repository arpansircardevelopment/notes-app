package com.arpansircar.notes_app.presentation.base

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.arpansircar.notes_app.di.ApplicationContainerRoot
import com.arpansircar.notes_app.di.AuthContainerRoot
import com.arpansircar.notes_app.di.HomeContainerRoot

open class BaseFragment : Fragment() {

    private val applicationContainerRoot: ApplicationContainerRoot by lazy {
        (requireActivity() as BaseActivity).appContainerRoot
    }

    protected val authContainerRoot: AuthContainerRoot by lazy {
        AuthContainerRoot(applicationContainerRoot, this)
    }

    protected val homeContainerRoot: HomeContainerRoot by lazy {
        HomeContainerRoot(applicationContainerRoot, this)
    }

    protected fun initializeBackPressedDispatcher(fragmentContext: Fragment) {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    homeContainerRoot.screensNavigator.triggerActivityFinish(fragmentContext)
                }
            })
    }
}