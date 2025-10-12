package com.example.tbcacademy

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityMainBinding
import com.example.tbcacademy.utils.trimmedTextValue

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

    private fun setListeners() = with(binding) {
        btnAddUser.setOnClickListener { view ->
            if (areFieldsEmpty()) {
                twResultMessage.text = getString(R.string.please_fill_out_all_the_fields)
                twResultMessage.setTextColor(Color.RED)
                return@setOnClickListener
            }

            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()
            val age = etAgeField.trimmedTextValue().toIntOrNull()

            age?.let {
                if (it <= 0) {
                    twResultMessage.text = getString(R.string.invalid_age)
                    twResultMessage.setTextColor(Color.RED)
                    return@setOnClickListener
                }
            } ?: return@setOnClickListener

            if (!isEmailValid(email)) {
                twResultMessage.text = getString(R.string.invalid_email)
                twResultMessage.setTextColor(Color.RED)
                return@setOnClickListener
            }
            if (userMap.containsKey(email)) {
                twResultMessage.text = getString(R.string.user_already_exists)
                twResultMessage.setTextColor(Color.RED)
                clearFields()

            } else {
                userMap[email] = User(firstName, lastName, age)
                twResultMessage.text = getString(R.string.user_added_successfully)
                twResultMessage.setTextColor(Color.GREEN)
                clearFields()
            }
            twActiveUsers.text = getString(R.string.active_users, userMap.size)
            hideKeyboard(view)
        }

        btnRemoveUser.setOnClickListener { view ->

            val email = etEmailField.trimmedTextValue()

            if (email.isEmpty()) {
                binding.twResultMessage.text = getString(R.string.please_fill_out_all_the_fields)
                binding.twResultMessage.setTextColor(Color.RED)
                return@setOnClickListener
            }

            val deletedUser = userMap.remove(email)

            if (deletedUser != null) {
                deletedUsers[email] = deletedUser
                twResultMessage.text = getString(R.string.user_deleted_successfully)
                twResultMessage.setTextColor(Color.GREEN)
                twDeletedUsers.text = getString(R.string.deleted_users, deletedUsers.size.toString())
                clearFields()
            } else {
                twResultMessage.text = getString(R.string.user_doesn_t_exist)
                twResultMessage.setTextColor(Color.RED)
            }
            twActiveUsers.text = getString(R.string.active_users, userMap.size)
            hideKeyboard(view)
        }

        btnUpdateUser.setOnClickListener { view ->
            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()
            val age = etAgeField.trimmedTextValue().toIntOrNull()

            if (areFieldsEmpty() || age == null) {
                twResultMessage.text = getString(R.string.please_fill_out_all_the_fields)
                twResultMessage.setTextColor(Color.RED)
                return@setOnClickListener
            }
            if (userMap.containsKey(email)) {
                userMap[email] = User(firstName, lastName, age)
                twResultMessage.text = getString(R.string.user_updated_successfully)
                twResultMessage.setTextColor(Color.GREEN)
            } else {
                twResultMessage.text = getString(R.string.user_doesn_t_exist)
                twResultMessage.setTextColor(Color.RED)
            }
            twActiveUsers.text = getString(R.string.active_users, userMap.size)
            clearFields()
            hideKeyboard(view)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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

    private fun hideKeyboard(view: android.view.View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}
