package com.example.tbcacademy.presentation.screens.login.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentLoginBinding
import com.example.tbcacademy.presentation.screens.login.vm.LoginUiState
import com.example.tbcacademy.presentation.screens.login.vm.LoginViewModel
import com.example.tbcacademy.presentation.screens.login.vm.LoginViewModelFactory
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.example.tbcacademy.utils.extensions.trimmedTextValue
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(requireContext())
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmailFieldLogin.trimmedTextValue()
                val password = etPasswordFieldLogin.trimmedTextValue()
                viewModel.login(email = email, password = password)
            }
            observeUiState()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is LoginUiState.Loading -> binding.root.showSnackBar(getString(R.string.loading))
                        is LoginUiState.Success -> {
                            binding.root.showSnackBar(getString(R.string.login_successful))
                            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                            viewModel.resetState()
                        }

                        is LoginUiState.Error -> {
                            binding.root.showSnackBar(state.message)
                            viewModel.resetState()
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}