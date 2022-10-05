package com.arpansircar.notes_app.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.common.NotesApplication
import com.arpansircar.notes_app.databinding.ActivityMainBinding
import com.arpansircar.notes_app.di.ApplicationContainer
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var appContainer: ApplicationContainer? = null

    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    private val btNavDestinationList: List<Int> = listOf(
        R.id.fragment_home, R.id.fragment_account
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (application as NotesApplication).appContainer
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_view) as NavHostFragment
        navController = navHostFragment.navController

        navController?.let {
            binding?.btNavView?.setupWithNavController(it)
        }

        navController?.addOnDestinationChangedListener { _, navDestination, _ ->
            if (navDestination.id in btNavDestinationList) {
                binding?.btNavView?.visibility = View.VISIBLE
            } else {
                binding?.btNavView?.visibility = View.GONE
            }
        }

        authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                navController?.navigate(R.id.fragment_login)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authStateListener?.let { FirebaseAuth.getInstance().addAuthStateListener(it) }

        appContainer?.firebaseAuth?.currentUser?.let {
            if (it.displayName == null || it.displayName?.isEmpty() == true) {
                navController?.navigate(R.id.fragment_user_details)
            } else {
                navController?.navigate(R.id.fragment_home)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        authStateListener?.let { FirebaseAuth.getInstance().removeAuthStateListener(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        appContainer = null
    }
}