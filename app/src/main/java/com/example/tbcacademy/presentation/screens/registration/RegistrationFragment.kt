package com.example.tbcacademy.presentation.screens.registration

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentRegistrationBinding
import com.example.tbcacademy.presentation.screens.registration.vm.RegisterEvent
import com.example.tbcacademy.presentation.screens.registration.vm.RegistrationViewModel
import com.example.tbcacademy.presentation.viewmodel.ViewModelFactory
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.example.tbcacademy.utils.extensions.trimmedTextValue
import kotlinx.coroutines.launch

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    private val viewModel: RegistrationViewModel by lazy {
        ViewModelFactory.createRegisterViewModelFactory(requireContext())
            .create(RegistrationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeEvents()
    }

    private fun setupUI() = with(binding) {
        btnRegister.setOnClickListener {
            val email = etEmailField.trimmedTextValue()
            val password = etPasswordField.trimmedTextValue()
            val repeat = etRepeatPasswordField.trimmedTextValue()

            when {
                email.isEmpty() -> root.showSnackBar("Please enter email")
                !email.matches(Regex(".+@.+\\..+")) -> root.showSnackBar("Invalid email format")
                password.isEmpty() -> root.showSnackBar("Please enter password")
                repeat.isEmpty() -> root.showSnackBar("Please repeat password")
                password != repeat -> root.showSnackBar("Passwords don't match")
                else -> viewModel.register(email, password)
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is RegisterEvent.NavigateBack -> {
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