package com.example.tbcacademy.presentation.fragment.splash.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.data.datastore.UserPreferences
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            kotlinx.coroutines.delay(1500)

            val isLoggedIn = userPreferences.isLoggedIn.first()
            val currentUser = auth.currentUser

            if (isLoggedIn && currentUser != null) {
                findNavController().navigate(
                    R.id.action_splashFragment_to_homeFragment
                )
            } else {
                if (isLoggedIn && currentUser == null) {
                    userPreferences.clearLogin()
                }

                findNavController().navigate(
                    R.id.action_splashFragment_to_welcomeFragment
                )
            }
        }
    }
}