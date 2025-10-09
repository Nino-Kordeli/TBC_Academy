package com.example.tbcacademy

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityRegistrationStep1Binding
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationStep1Binding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrationStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        setListeners()
    }

    private fun setListeners() = with(binding) {
        btnNext.setOnClickListener {
            val email = etEmailFieldRegistration.getString()
            val password = etPasswordFieldRegistration.getString()

            if (areFieldsEmpty(email, password)) return@setOnClickListener
            if (!isEmailValid(email)) {
                makeToast(getString(R.string.enter_a_valid_email))
                return@setOnClickListener
            }
            if (!isPasswordValid(password)) {
                makeToast(getString(R.string.invalid_password_message))
                return@setOnClickListener
            }
            registerUser(email, password)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun areFieldsEmpty(email: String, password: String): Boolean = with(binding) {
        if (email.isEmpty() || password.isEmpty()) {
            makeToast(getString(R.string.fill_out_all_the_fields))
            return true
        } else {
            false
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    makeToast(getString(R.string.account_created_successfully))
                    val intent = Intent(this, RegistrationActivityStep2::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                } else {
                    val message = task.exception?.message ?: getString(R.string.registration_failed)
                    makeToast(message)
                }
            }
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password.length < 6) return false
        if (!password.any { it.isUpperCase() }) return false
        return true
    }

    private fun makeToast(text: String) {
        Toast.makeText(this@RegistrationActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun EditText.getString() = this.text.toString().trim()
}