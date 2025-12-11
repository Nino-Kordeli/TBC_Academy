package com.example.tbcacademy.presentation.fragment.welcome

import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentWelcomeBinding

class WelcomeFragment :
    BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    override fun bind() {
        setupClicks()
    }

    private fun setupClicks() {
        binding.btnContinueButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_welcomeFragment_to_registerFragment
            )
        }
    }
}
