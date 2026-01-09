package com.example.tbcacademy.presentation.screens.registration.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.databinding.FragmentRegistrationBinding
import com.example.tbcacademy.presentation.common.BaseFragment
import com.example.tbcacademy.presentation.screens.registration.vm.RegisterEvent
import com.example.tbcacademy.presentation.screens.registration.vm.RegistrationViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()
    override fun bind() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeEvents()
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmailField.text.toString().trim()
            val password = binding.etPasswordField.text.toString().trim()
            val confirmPassword = binding.etRepeatPasswordField.text.toString().trim()

            viewModel.register(email, password, confirmPassword)
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is RegisterEvent.RegisterSuccess -> {
                            val bundle = Bundle().apply {
                                putString("email", event.email)
                                putString("password", event.password)
                            }
                            parentFragmentManager.setFragmentResult("register_success", bundle)
                            findNavController().popBackStack()
                        }
                        is RegisterEvent.ShowError -> {
                            binding.root.showSnackBar(event.message)
                        }
                    }
                }
            }
        }
    }
}