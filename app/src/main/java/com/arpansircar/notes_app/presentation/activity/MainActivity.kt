package com.arpansircar.notes_app.presentation.activity

import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.arpansircar.notes_app.R
import com.arpansircar.notes_app.databinding.ActivityMainBinding
import com.arpansircar.notes_app.presentation.base.BaseActivity


class MainActivity : BaseActivity() {

    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_view) as NavHostFragment
        navController = navHostFragment.navController

        WindowCompat.getInsetsController(window, window.decorView).also {
            it.isAppearanceLightStatusBars = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}