package com.example.tbcacademy.presentation.screens.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentLoginBinding
import com.example.tbcacademy.presentation.screens.login.vm.LoginViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeState()
        observeEffects()
    }

    private fun setupUI() = binding.apply {
        btnLogin.setOnClickListener {
            viewModel.onEvent(
                LoginEvent.Submit(
                    etEmailLoginField.text.toString(),
                    etPasswordLoginField.text.toString()
                )
            )
        }

        btnRegister.setOnClickListener {
            viewModel.onEvent(LoginEvent.NavigateToRegister)
        }

        etEmailLoginField.doAfterTextChanged {
            viewModel.onEvent(LoginEvent.EmailChanged(it.toString()))
        }

        etPasswordLoginField.doAfterTextChanged {
            viewModel.onEvent(LoginEvent.PasswordChanged(it.toString()))
        }

        rememberMeCheckBox.setOnCheckedChangeListener { _, checked ->
            viewModel.onEvent(LoginEvent.RememberMeToggled(checked))
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        LoginEffect.NavigateToHome ->
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                        is LoginEffect.ShowError ->
                            binding.root.showSnackBar(effect.message)

                        LoginEffect.NavigateToRegister ->
                            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                    }
                }
            }
        }
    }
}