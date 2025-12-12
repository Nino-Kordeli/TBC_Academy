package com.example.tbcacademy.presentation.fragment.login.fragment

import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.common.extensions.showSnackBar
import com.example.tbcacademy.data.datastore.UserPreferences
import com.example.tbcacademy.databinding.FragmentLoginBinding
import com.example.tbcacademy.presentation.fragment.login.contract.LoginEvent
import com.example.tbcacademy.presentation.fragment.login.contract.LoginSideEffect
import com.example.tbcacademy.presentation.fragment.login.vm.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun bind() {
        autoLoginCheck()
        setupListeners()
        observeState()
        observeSideEffects()
    }

    private fun autoLoginCheck() {
        viewLifecycleOwner.lifecycleScope.launch {
            userPreferences.getEmail
                .take(1)
                .collect { email ->
                    if (email.isNotEmpty()) {
                        findNavController().navigate(
                            R.id.action_loginFragment_to_homeFragment
                        )
                    }
                }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.btnLogin.isEnabled = !state.loading
                }
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        is LoginSideEffect.NavigateToHome -> {
                            binding.root.showSnackBar("Login successful")
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }

                        is LoginSideEffect.ShowSnackBar -> {
                            Toast.makeText(requireContext(), effect.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() = with(binding) {

        etEmailField.doAfterTextChanged { text ->
            viewModel.onEvent(LoginEvent.EmailChanged(text.toString().trim()))
        }

        etPasswordField.doAfterTextChanged { text ->
            viewModel.onEvent(LoginEvent.PasswordChanged(text.toString().trim()))
        }

        rememberMeCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(LoginEvent.RememberMeChanged(isChecked))
        }

        btnLogin.setOnClickListener {
            viewModel.onEvent(LoginEvent.Submit)
        }

        btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}
