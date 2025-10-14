package com.example.tbcacademy.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.ActivityUserFormBinding
import com.example.tbcacademy.model.OperationType
import com.example.tbcacademy.model.User
import com.example.tbcacademy.utils.ui.hide
import com.example.tbcacademy.utils.ui.hideKeyboard
import com.example.tbcacademy.utils.ui.show
import com.example.tbcacademy.utils.ui.trimmedTextValue
import com.example.tbcacademy.utils.validation.isValidEmail

class UserFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFormBinding
    private var operationType = OperationType.ADD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        operationType = intent.getParcelableExtra(
            UserDashboardActivity.OPERATION_TYPE,
            OperationType::class.java
        ) ?: OperationType.ADD

        configureUI()
        setListeners()
    }

    private fun configureUI() = with(binding) {
        when (operationType) {
            OperationType.ADD -> {
                btnAddUser.show()
                btnUpdateUser.hide()
                btnRemoveUser.hide()
            }

            else -> {
                btnAddUser.hide()
                btnUpdateUser.show()
                btnRemoveUser.show()
                val user = intent.getParcelableExtra(
                    UserDashboardActivity.USER,
                    User::class.java
                )
                user?.let { fillUserData(it) }
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
            val age = etAgeField.trimmedTextValue().toIntOrNull()

            age?.let {
                if (it <= 0) {
                    handleError(R.string.invalid_age)
                    return@setOnClickListener
                }
            } ?: return@setOnClickListener

            if (!email.isValidEmail()) {
                handleError(R.string.invalid_email)
                return@setOnClickListener
            }

            val user = User(
                firstName = firstName,
                lastName = lastName,
                age = age,
                email = email
            )

            val resultIntent = Intent().apply {
                putExtra(UserDashboardActivity.OPERATION_TYPE, OperationType.ADD)
                putExtra(UserDashboardActivity.USER, user)
            }
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }

    private fun setRemoveButtonListener() = with(binding) {
        btnRemoveUser.setOnClickListener { view ->
            view.hideKeyboard()

            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()
            val age = etAgeField.trimmedTextValue().toIntOrNull()

            age?.let {
                if (it <= 0) {
                    handleError(R.string.invalid_age)
                    return@setOnClickListener
                }
            } ?: return@setOnClickListener

            val user = User(
                firstName = firstName,
                lastName = lastName,
                age = age,
                email = email
            )

            val resultIntent = Intent().apply {
                putExtra(UserDashboardActivity.OPERATION_TYPE, OperationType.REMOVE)
                putExtra(UserDashboardActivity.USER, user)
            }
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }

    private fun setUpdateUserListener() = with(binding) {
        btnUpdateUser.setOnClickListener { view ->
            view.hideKeyboard()
            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()
            val age = etAgeField.trimmedTextValue().toIntOrNull()

            if (areFieldsEmpty()) {
                handleError(R.string.please_fill_out_all_the_fields)
                return@setOnClickListener
            }
            age?.let {
                if (it <= 0) {
                    handleError(R.string.invalid_age)
                    return@setOnClickListener
                }
            } ?: return@setOnClickListener

            val user = User(
                firstName = firstName,
                lastName = lastName,
                age = age,
                email = email
            )

            val resultIntent = Intent().apply {
                putExtra(UserDashboardActivity.OPERATION_TYPE, OperationType.UPDATE)
                putExtra(UserDashboardActivity.USER, user)
            }
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }

    private fun areFieldsEmpty(): Boolean = with(binding) {
        val inputs = listOf(
            etEmailField.trimmedTextValue(),
            etNameField.trimmedTextValue(),
            etLastnameField.trimmedTextValue(),
            etAgeField.trimmedTextValue()
        )
        return inputs.any { it.isEmpty() }
    }

    private fun handleError(@StringRes resId: Int) = with(binding) {
        twErrorMessage.text = getString(resId)
    }
}
