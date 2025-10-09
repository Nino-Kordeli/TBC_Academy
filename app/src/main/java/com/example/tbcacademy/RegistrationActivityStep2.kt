package com.example.tbcacademy

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityRegistrationStep2Binding
import com.google.android.material.snackbar.Snackbar

class RegistrationActivityStep2 : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationStep2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrationStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
    }

    private fun setListeners() = with(binding) {
        btnSignUp.setOnClickListener {
            val username = etUserNameField.getString()
            if (username.isEmpty()) {
                showSnackbar(getString(R.string.please_enter_a_username))
                return@setOnClickListener
            }

            val email = intent.getStringExtra("email")

            val completeIntent = Intent(
                this@RegistrationActivityStep2,
                RegistrationCompleteActivity::class.java
            )
            completeIntent.putExtra("email", email)
            startActivity(completeIntent)
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun EditText.getString() = this.text.toString().trim()
}
