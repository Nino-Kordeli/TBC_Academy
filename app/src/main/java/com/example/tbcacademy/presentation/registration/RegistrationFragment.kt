package com.example.tbcacademy.presentation.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentRegistrationBinding
import com.example.tbcacademy.presentation.registration.vm.RegistrationViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.example.tbcacademy.utils.validation.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>() {

    override val viewModel: RegistrationViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding = FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            if (validateFields()) {
                saveCredentials()
                clearFields()
                binding.root.showSnackBar("Credentials saved successfully")
            }
        }

        binding.btnRead.setOnClickListener {
            readCredentials()
        }
    }

    private fun validateFields(): Boolean = with(binding) {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()

        etFirstName.error = null
        etLastName.error = null
        etEmail.error = null

        when {
            firstName.isEmpty() -> {
                etFirstName.error = getString(R.string.first_name_is_required)
                etFirstName.requestFocus()
                return false
            }

            lastName.isEmpty() -> {
                etLastName.error = getString(R.string.last_name_is_required)
                etLastName.requestFocus()
                return false
            }

            email.isEmpty() -> {
                etEmail.error = getString(R.string.email_is_required)
                etEmail.requestFocus()
                return false
            }

            !isValidEmail(email) -> {
                etEmail.error = getString(R.string.please_enter_a_valid_email)
                etEmail.requestFocus()
                return false
            }
        }
        return true
    }

    private fun saveCredentials() = with(binding) {
        val first = etFirstName.text.toString().trim()
        val last = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        viewModel.save(first, last, email)
    }

    private fun readCredentials() {
        viewModel.load { creds ->
            binding.tvUserCredentials.text =
                getString(R.string.name_email, creds.firstName, creds.lastName, creds.email)
        }
    }

    private fun clearFields() = with(binding) {
        etFirstName.text?.clear()
        etLastName.text?.clear()
        etEmail.text?.clear()
        etFirstName.requestFocus()
    }
}