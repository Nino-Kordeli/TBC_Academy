package com.example.tbcacademy.presentation.fragment.register.fragment

import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentRegisterBinding
import com.example.tbcacademy.presentation.fragment.register.contract.RegisterEvent
import com.example.tbcacademy.presentation.fragment.register.contract.RegisterSideEffect
import com.example.tbcacademy.presentation.fragment.register.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun bind() {
        setListeners()
        observeState()
        observeSideEffects()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.btnRegister.isEnabled = !state.loading
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        etEmailField.doAfterTextChanged { text ->
            viewModel.onEvent(RegisterEvent.EmailChanged(text.toString().trim()))
        }
        etPasswordField.doAfterTextChanged { text ->
            viewModel.onEvent(RegisterEvent.PasswordChanged(text.toString().trim()))
        }
        etRepeatPasswordField.doAfterTextChanged { text ->
            viewModel.onEvent(RegisterEvent.ConfirmPasswordChanged(text.toString().trim()))
        }
        btnRegister.setOnClickListener {
            viewModel.onEvent(RegisterEvent.Submit)
        }
        btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        RegisterSideEffect.NavigateToHome -> {
                            findNavController().navigate(
                                R.id.action_registerFragment_to_loginFragment
                            )
                        }

                        is RegisterSideEffect.ShowToast -> {
                            Toast.makeText(requireContext(), effect.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }
}