package com.example.tbcacademy.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.FragmentUserFormBinding
import com.example.tbcacademy.model.OperationType
import com.example.tbcacademy.model.User
import com.example.tbcacademy.utils.extensions.hide
import com.example.tbcacademy.utils.extensions.hideKeyboard
import com.example.tbcacademy.utils.extensions.show
import com.example.tbcacademy.utils.extensions.trimmedTextValue
import com.example.tbcacademy.utils.validation.isValidEmail

class UserFormFragment : Fragment() {

    private var _binding: FragmentUserFormBinding? = null
    private val binding get() = _binding!!

    private val args: UserFormFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserFormBinding.inflate(inflater, container, false)

        configureUI()
        setListeners()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        return binding.root
    }

    private fun configureUI() = with(binding) {
        when (args.operationType) {
            OperationType.ADD -> {
                btnAddUser.show()
                btnUpdateUser.hide()
                btnRemoveUser.hide()
            }

            else -> {
                btnAddUser.hide()
                btnUpdateUser.show()
                btnRemoveUser.show()
                args.user?.let { fillUserData(it) }
            }
        }
    }

    private fun fillUserData(user: User) = with(binding) {
        etEmailField.setText(user.email)
        etEmailField.isEnabled = false
        etNameField.setText(user.firstName)
        etLastnameField.setText(user.lastName)
        etAgeField.setText(user.age.toString())
    }

    private fun setListeners() {
        setAddButtonClickListener()
        setRemoveButtonListener()
        setUpdateUserListener()
    }

    private fun setAddButtonClickListener() = with(binding) {
        btnAddUser.setOnClickListener { view ->
            view.hideKeyboard()
            if (areFieldsEmpty()) {
                handleError(R.string.please_fill_out_all_the_fields)
                return@setOnClickListener
            }
            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()
            val age = etAgeField.trimmedTextValue().toIntOrNull() ?: run {
                handleError(R.string.invalid_age)
                return@setOnClickListener
            }
            if (age <= 0) {
                handleError(R.string.invalid_age)
                return@setOnClickListener
            }
            if (!isValidEmail(email)) {
                handleError(R.string.invalid_email)
                return@setOnClickListener
            }

            val newUser = User(firstName, lastName, age, email)
            sendResultAndNavigateBack(OperationType.ADD, newUser)
        }
    }

    private fun setRemoveButtonListener() = with(binding) {
        btnRemoveUser.setOnClickListener { view ->
            view.hideKeyboard()
            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()
            val age = etAgeField.trimmedTextValue().toIntOrNull() ?: return@setOnClickListener

            val removeUser = User(firstName, lastName, age, email)
            sendResultAndNavigateBack(OperationType.REMOVE, removeUser)
        }
    }

    private fun setUpdateUserListener() = with(binding) {
        btnUpdateUser.setOnClickListener { view ->
            view.hideKeyboard()
            if (areFieldsEmpty()) {
                handleError(R.string.please_fill_out_all_the_fields)
                return@setOnClickListener
            }
            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()
            val age = etAgeField.trimmedTextValue().toIntOrNull() ?: run {
                handleError(R.string.invalid_age)
                return@setOnClickListener
            }

            val updatedUser = User(firstName, lastName, age, email)
            sendResultAndNavigateBack(OperationType.UPDATE, updatedUser)
        }
    }

    private fun sendResultAndNavigateBack(operationType: OperationType, user: User) {
        setFragmentResult(
            "userFormResult",
            bundleOf(
                "operationType" to operationType,
                "user" to user
            )
        )
        findNavController().navigateUp()
    }

    private fun areFieldsEmpty(): Boolean = with(binding) {
        listOf(
            etEmailField.trimmedTextValue(),
            etNameField.trimmedTextValue(),
            etLastnameField.trimmedTextValue(),
            etAgeField.trimmedTextValue()
        ).any { it.isEmpty() }
    }

    private fun handleError(@StringRes resId: Int) =
        with(binding) { twErrorMessage.text = getString(resId) }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
