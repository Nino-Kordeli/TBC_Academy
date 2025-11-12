package com.example.tbcacademy.screen.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentRegisterBinding
import com.example.tbcacademy.screen.register.vm.RegisterUiState
import com.example.tbcacademy.screen.register.vm.RegisterViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.example.tbcacademy.utils.extensions.trimmedTextValue
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        setupButtonClick()
    }

    private fun setupButtonClick() = with(binding) {
        btnRegister.setOnClickListener {
            val email = etEmailField.trimmedTextValue()
            val password = etPasswordField.trimmedTextValue()
            val username = etUsernameField.trimmedTextValue()
            viewModel.register(email, password, username)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is RegisterUiState.Idle -> {}
                    is RegisterUiState.Loading -> binding.root.showSnackBar(getString(R.string.loading_))
                    is RegisterUiState.Success -> {
                        binding.root.showSnackBar(getString(R.string.registration_successful))
                        findNavController().navigate(R.id.action_registerFragment_to_welcomeFragment)
                        viewModel.resetState()
                    }

                    is RegisterUiState.Error -> {
                        binding.root.showSnackBar(state.message)
                        viewModel.resetState()
                    }
                }
            }
        }
    }
}
