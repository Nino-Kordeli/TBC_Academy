package com.example.tbcacademy

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityMainBinding
import com.example.tbcacademy.utils.ui.hideKeyboard
import com.example.tbcacademy.utils.ui.trimmedTextValue
import com.example.tbcacademy.utils.validation.isValidEmail

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userMap = mutableMapOf<String, User>()
    private val deletedUsers = mutableMapOf<String, User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
    }

    private fun setListeners() {
        setAddButtonClickListener()
        setRemoveButtonListener()
        setUpdateUserListener()
    }

    private fun setAddButtonClickListener() = with(binding) {
        btnAddUser.setOnClickListener { view ->
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

            if (userMap.containsKey(email)) {
                handleError(R.string.user_already_exists)
                clearFields()
            } else {
                userMap[email] = User(firstName, lastName, age)
                handleSuccess(R.string.user_added_successfully)
                clearFields()
            }

            twActiveUsers.text = getString(R.string.active_users, userMap.size)
            view.hideKeyboard()
        }
    }

    private fun setRemoveButtonListener() = with(binding) {
        btnRemoveUser.setOnClickListener { view ->
            val email = etEmailField.trimmedTextValue()

            if (email.isEmpty()) {
                handleError(R.string.please_fill_out_all_the_fields)
                return@setOnClickListener
            }

            val deletedUser = userMap.remove(email)

            deletedUser?.let {
                deletedUsers[email] = it
                handleSuccess(R.string.user_deleted_successfully)
                twDeletedUsers.text =
                    getString(R.string.deleted_users, deletedUsers.size.toString())
                clearFields()
            } ?: handleError(R.string.user_doesn_t_exist)

            twActiveUsers.text = getString(R.string.active_users, userMap.size)
            view.hideKeyboard()
        }
    }

    private fun setUpdateUserListener() = with(binding) {
        btnUpdateUser.setOnClickListener { view ->
            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()
            val age = etAgeField.trimmedTextValue().toIntOrNull()

            if (areFieldsEmpty() || age == null) {
                handleError(R.string.please_fill_out_all_the_fields)
                return@setOnClickListener
            }

            if (userMap.containsKey(email)) {
                userMap[email] = User(firstName, lastName, age)
                handleSuccess(R.string.user_updated_successfully)
            } else {
                handleError(R.string.user_doesn_t_exist)
            }

            twActiveUsers.text = getString(R.string.active_users, userMap.size)
            clearFields()
            view.hideKeyboard()
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

    private fun clearFields() = with(binding) {
        listOf(
            etNameField,
            etLastnameField,
            etEmailField,
            etAgeField
        ).forEach { it.text?.clear() }
    }

    private fun handleSuccess(@StringRes resId: Int) = with(binding) {
        twResultMessage.text = getString(resId)
        twResultMessage.setTextColor(Color.GREEN)
    }

    private fun handleError(@StringRes resId: Int) = with(binding) {
        twResultMessage.text = getString(resId)
        twResultMessage.setTextColor(Color.RED)
    }
}
