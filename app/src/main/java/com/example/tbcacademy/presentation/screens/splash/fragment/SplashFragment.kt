package com.example.tbcacademy.presentation.screens.splash.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.screens.splash.vm.SplashDestination
import com.example.tbcacademy.presentation.screens.splash.vm.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.destination.observe(viewLifecycleOwner) { dest ->
            when (dest) {
                is SplashDestination.Home -> {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }

                is SplashDestination.Login -> {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }
    }
}