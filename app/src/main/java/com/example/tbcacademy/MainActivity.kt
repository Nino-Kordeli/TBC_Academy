package com.example.tbcacademy

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityMainBinding
import com.example.tbcacademy.utils.showSnackBar
import com.example.tbcacademy.utils.trimmedTextValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userMap = mutableMapOf<String, User>()

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
        btnAddUser.setOnClickListener {
            val inputs = listOf(
                etEmailField.trimmedTextValue(),
                etNameField.trimmedTextValue(),
                etLastnameField.trimmedTextValue(),
                etAgeField.trimmedTextValue()
            )
            if (inputs.any { it.isEmpty() }) {
                it.showSnackBar(getString(R.string.please_fill_out_all_the_fields))
                return@setOnClickListener
            }

            val age = etAgeField.trimmedTextValue().toIntOrNull()
            if (age == null) {
                it.showSnackBar(getString(R.string.invalid_age))
                return@setOnClickListener
            }
            val email = etEmailField.trimmedTextValue()
            val firstName = etNameField.trimmedTextValue()
            val lastName = etLastnameField.trimmedTextValue()

            if (userMap.containsKey(email)) {
                it.showSnackBar(getString(R.string.email_already_taken))
                twResultMessage.text = getString(R.string.email_unavailable)
                twResultMessage.setTextColor(Color.RED)
                listOf(
                    etNameField,
                    etLastnameField,
                    etEmailField,
                    etAgeField
                ).forEach { it.text?.clear() }

            } else {
                userMap[email] = User(firstName, lastName, age)
                it.showSnackBar(getString(R.string.user_added_successfully))
                twResultMessage.text = (getString(R.string.user_added))
                twResultMessage.setTextColor(Color.GREEN)
            }
        }
    }
}
