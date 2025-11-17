package com.example.tbcacademy.presentation.screens.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentLoginBinding
import com.example.tbcacademy.presentation.screens.login.vm.LoginEvent
import com.example.tbcacademy.presentation.screens.login.vm.LoginViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.example.tbcacademy.utils.extensions.trimmedTextValue
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeEvents()
        checkAutoLogin()
        setupResultListener()
    }

    private fun setupUI() = with(binding) {
        btnLogin.setOnClickListener {
            val email = etEmailLoginField.trimmedTextValue()
            val password = etPasswordLoginField.trimmedTextValue()
            viewModel.login(email, password, rememberMeCheckBox.isChecked)
        }
        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val emailValid = etEmailLoginField.trimmedTextValue().matches(Regex(".+@.+\\..+"))
                val passNotEmpty = etPasswordLoginField.trimmedTextValue().isNotEmpty()
                btnLogin.isEnabled = emailValid && passNotEmpty
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        etEmailLoginField.addTextChangedListener(watcher)
        etPasswordLoginField.addTextChangedListener(watcher)
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is LoginEvent.NavigateToHome -> {
                        findNavController().apply {
                            navigate(R.id.action_loginFragment_to_homeFragment)
                            popBackStack(R.id.loginFragment, true)
                        }
                    }
                    is LoginEvent.ShowError -> binding.root.showSnackBar(event.message)
                }
            }
        }
    }

    private fun checkAutoLogin() {
        if (viewModel.isLoggedIn() && viewModel.isRememberMe()) {
            findNavController().navigate(R.id.action_global_homeFragment)
        }
    }

    private fun setupResultListener() {
        parentFragmentManager.setFragmentResultListener("register_success", viewLifecycleOwner) { _, bundle ->
            val email = bundle.getString("email") ?: ""
            val password = bundle.getString("password") ?: ""
            binding.etEmailLoginField.setText(email)
            binding.etPasswordLoginField.setText(password)
        }
    }
}