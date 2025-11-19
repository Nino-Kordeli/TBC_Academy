package com.example.tbcacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.tbcacademy.data.repository.SessionRepositoryImpl
import com.example.tbcacademy.domain.usecase.CheckSessionUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        checkSessionAndNavigate()
    }

    private fun checkSessionAndNavigate() {
        lifecycleScope.launch {
            val sessionRepo = SessionRepositoryImpl(this@MainActivity)
            val hasSession = CheckSessionUseCase(sessionRepo).invoke()

            if (hasSession) {
                navController.navigate(R.id.homeFragment)
            }
        }
    }
}