package com.example.tbcacademy

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        with(binding) {
            saveButton.setOnClickListener {
                if (!isFieldEmpty()) {
                    if (isUsernameValid() && isEmailValid() && isAgeValid()) {
                        firstPage.visibility = View.GONE
                        secondPage.visibility = View.VISIBLE
                        printUserCredentials()
                    }
                }
                if (isFieldEmpty()) {
                    makeToast("All fields should be filled out")
                }
                isUsernameValid()
                isEmailValid()
                isAgeValid()
            }

            clearButton.setOnLongClickListener {
                clearAllFields()
                true
            }

            againButton.setOnClickListener {
                secondPage.visibility = View.GONE
                firstPage.visibility = View.VISIBLE
                clearAllFields()
            }
        }
    }

    private fun isFieldEmpty(): Boolean = with(binding) {
        val fieldName = listOf(
            emailField to "Email",
            usernameField to "Username",
            firstNameField to "First Name",
            lastNameField to "Last Name",
            ageField to "Age"
        )
        var isEmpty = false
        fieldName.forEach { (field, name) ->
            if (field.text.isNullOrBlank()) {
                field.error = "$name field can not be empty"
                isEmpty = true
            }
        }
        return isEmpty
    }

    private fun isUsernameValid(): Boolean = with(binding) {
        if (usernameField.text.toString().length < 10) {
            makeToast("Username too short")
            false
        } else true
    }

    private fun isEmailValid(): Boolean = with(binding) {
        val email = emailField.text.toString()
        return if ((!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            makeToast("Invalid Email")
            false
        } else true
    }

    private fun isAgeValid(): Boolean = with(binding) {
        val ageText = ageField.text.toString()
        val age = ageText.toIntOrNull()
        return if (age == null || age <= 0) {
            ageField.error = "Age must be above zero"
            false
        } else {
            true
        }
    }

    private fun clearAllFields() = with(binding) {
        listOf(emailField, usernameField, firstNameField, lastNameField, ageField)
            .forEach { it.text?.clear() }
    }

    private fun printUserCredentials() = with(binding) {
        val firstName = firstNameField.text.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        val lastName = lastNameField.text.toString()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        val fullName = "$firstName $lastName"
        nameResult.text = fullName
        userNameResult.text = usernameField.text
        emailResult.text = emailField.text
        ageResult.text = getString(R.string.age) + ": ${ageField.text}"
    }

    private fun makeToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }
}
