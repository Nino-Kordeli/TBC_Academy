package com.example.tbcacademy

import android.os.Bundle
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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.saveButton.setOnClickListener {
            if (!isFieldEmpty()) {
                if (isUsernameValid() && isEmailValid() && isAgeValid()) {
                    binding.firstPage.visibility = View.GONE
                    binding.secondPage.visibility = View.VISIBLE
                }
            }
            if (isFieldEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "All fields should be filled out",
                    Toast.LENGTH_LONG
                ).show()
            }
            isUsernameValid()
            isEmailValid()
            isAgeValid()
        }
        binding.clearButton.setOnLongClickListener {
            clearAllFields()
            true//ანუ ნორმალ კლიკი აღარ დატრიგერდება
        }

        binding.againButton.setOnClickListener {
            binding.secondPage.visibility = View.GONE
            binding.firstPage.visibility = View.VISIBLE
            clearAllFields()
        }
    }

    private fun isFieldEmpty(): Boolean {
        val fieldName = with(binding) {
            listOf(
                emailField to "Email",
                usernameField to "Username",
                firstNameField to "First Name",
                lastNameField to "Last Name",
                ageField to "Age"
            )
        }
        var isEmpty = false
        fieldName.forEach { (field, name) ->
            if (field.text.isNullOrBlank()) {
                field.error = "$name field can not be empty"
                isEmpty = true
            }
        }
        return isEmpty
    }

    private fun isUsernameValid(): Boolean {
        if (binding.usernameField.text.toString().length < 10) {
            Toast.makeText(
                this@MainActivity,
                "Username too short",
                Toast.LENGTH_SHORT
            ).show()
            false
        } else if (binding.usernameField.text.toString().length > 14) {
            Toast.makeText(
                this@MainActivity,
                "Username too long",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    private fun isEmailValid(): Boolean {
        val email = binding.emailField.text
        var found = false

        email?.forEach { char ->
            if (char == '@') {
                found = true
            }
        }
        return if (!found) {
            Toast.makeText(this@MainActivity, "Invalid Email", Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    private fun isAgeValid(): Boolean {
        val ageText = binding.ageField.text.toString()
        val age = ageText.toIntOrNull()
        return if (age == null || age <= 0) {
            binding.ageField.error = "Age must be above zero"
            false
        } else {
            true
        }
    }

    private fun clearAllFields() = with(binding) {
        listOf(emailField, usernameField, firstNameField, lastNameField, ageField)
            .forEach { it.text?.clear() }
    }
}
