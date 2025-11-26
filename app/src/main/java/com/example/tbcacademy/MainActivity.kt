package com.example.tbcacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.tbcacademy.domain.repository.SessionRepository
import com.example.tbcacademy.domain.usecase.CheckSessionUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    @Inject
    lateinit var sessionRepository: SessionRepository

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
            val hasSession = CheckSessionUseCase(sessionRepository).invoke()

            if (hasSession) {
                navController.navigate(R.id.homeFragment)
            }
        }
    }
}