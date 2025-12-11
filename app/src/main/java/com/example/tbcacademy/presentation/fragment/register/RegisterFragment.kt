package com.example.tbcacademy.presentation.fragment.register

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentRegisterBinding
import com.example.tbcacademy.presentation.auth.AuthEvent
import com.example.tbcacademy.presentation.auth.AuthSideEffect
import com.example.tbcacademy.presentation.auth.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()

    override fun bind() {
        setupClicks()
        observeSideEffects()
    }

    private fun setupClicks() {
        binding.btnRegister.setOnClickListener {
            viewModel.onEvent(AuthEvent.OnRegister)
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        AuthSideEffect.NavigateHome -> {
                            findNavController().navigate(
                                R.id.action_registerFragment_to_welcomeFragment
                            )
                        }
                    }
                }
            }
        }
    }
}
