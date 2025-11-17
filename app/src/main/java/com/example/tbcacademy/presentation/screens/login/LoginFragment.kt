package com.example.tbcacademy.presentation.screens.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentLoginBinding
import com.example.tbcacademy.presentation.screens.login.vm.LoginEvent
import com.example.tbcacademy.presentation.screens.login.vm.LoginViewModel
import com.example.tbcacademy.presentation.viewmodel.ViewModelFactory
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.example.tbcacademy.utils.extensions.trimmedTextValue
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by lazy {
        ViewModelFactory.createLoginViewModelFactory(requireContext())
            .create(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeEvents()
        validateAndLogin()
    }

    private fun setupUI() = binding.apply {
        btnLogin.setOnClickListener {
            val email = etEmailLoginField.trimmedTextValue()
            val password = etPasswordLoginField.trimmedTextValue()
            val rememberMe = rememberMeCheckBox.isChecked
            viewModel.login(email, password, rememberMe)
        }
        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is LoginEvent.NavigateToHome -> findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    is LoginEvent.ShowError -> binding.root.showSnackBar(event.message)
                }
            }
        }
    }

    private fun validateAndLogin() = with(binding) {
        val email = etEmailLoginField.text.toString().trim()
        val password = etPasswordLoginField.text.toString()
        val remember = rememberMeCheckBox.isChecked

        when {
            email.isEmpty() && password.isEmpty() -> {
                root.showSnackBar("Please enter email and password")
                return@with
            }

            email.isEmpty() -> {
                root.showSnackBar("Please enter your email")
                return@with
            }

            password.isEmpty() -> {
                root.showSnackBar("Please enter your password")
                return@with
            }

            else -> {
                viewModel.login(email, password, remember)
            }
        }
    }
}
