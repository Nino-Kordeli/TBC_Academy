package com.example.tbcacademy

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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

        handleIntent(intent)

        checkSessionAndNavigate()
        checkPostNotificationPermission()
    }

    private fun checkSessionAndNavigate() {
        lifecycleScope.launch {
            val hasSession = CheckSessionUseCase(sessionRepository).invoke()

            if (hasSession) {
                navController.navigate(R.id.homeFragment)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (uri.scheme == "tbcacademy" && uri.host == "profile") {
                lifecycleScope.launch {
                    val hasSession = CheckSessionUseCase(sessionRepository).invoke()
                    if (hasSession) {
                        navController.navigate(R.id.profileFragment)
                    } else {

                        navController.navigate(R.id.loginFragment)
                    }
                }
            }
        }
    }


    private fun checkPostNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }
    }
}