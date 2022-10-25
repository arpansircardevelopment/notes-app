package com.arpansircar.notes_app.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    private val btNavDestinationList: List<Int> = listOf(
        R.id.fragment_home, R.id.fragment_account
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}