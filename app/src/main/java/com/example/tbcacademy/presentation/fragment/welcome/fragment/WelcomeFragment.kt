package com.example.tbcacademy.presentation.fragment.welcome.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.data.datastore.UserPreferences
import com.example.tbcacademy.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment :
    BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    @Inject
    lateinit var userPreferences: UserPreferences
    override fun bind() {
        setListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAutoLogin()
        setListeners()
    }

    private fun checkAutoLogin() {
        viewLifecycleOwner.lifecycleScope.launch {
            userPreferences.getEmail.collect { email ->
                if (email.isNotEmpty()) {
                    findNavController().navigate(
                        R.id.action_welcomeFragment_to_homeFragment
                    )
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnContinueButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_welcomeFragment_to_registerFragment
            )
        }
    }
}