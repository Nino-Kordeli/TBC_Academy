package com.example.tbcacademy.screen.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentWelcomeBinding


class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, Nothing>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding {
        return FragmentWelcomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterWelcome.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }
}
