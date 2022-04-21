package com.sopian.challenge5.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.sopian.challenge5.Injection
import com.sopian.challenge5.R
import com.sopian.challenge5.storage.SharedPreferencesStorage
import com.sopian.challenge5.storage.Storage
import com.sopian.challenge5.ui.login.LoginFragment.Companion.IS_LOGGED_IN
import com.sopian.challenge5.utils.EdgeToEdgeUtils

class MainActivity : AppCompatActivity() {

    private lateinit var storage: Storage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storage = SharedPreferencesStorage(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val isLoggedIn = storage.getBoolean(IS_LOGGED_IN)
        if (isLoggedIn) {
            navController.navigate(R.id.action_global_homeFragment)
        } else {
            navController.navigate(R.id.action_global_loginFragment)
        }

        EdgeToEdgeUtils.applyEdgeToEdge(window)
    }
}