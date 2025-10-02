package com.example.tbcacademy

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val users = mutableMapOf<String, User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            addUserButton.setOnClickListener {
                if (isEmailValid()) {
                    inputValidation(
                        name = nameField.getInputText(),
                        email = emailField.getInputText()
                    )
                }
            }
            getUserInfoButton.setOnClickListener {
                getUserInfo(
                    email = emailField.getInputText()
                )
            }
        }
    }

    private fun inputValidation(name: String, email: String) = with(binding) {
        if (users.containsKey(email)) makeToast("This email is taken")

        if (name.isEmpty() || email.isEmpty()) {
            makeToast("Fill out all the fields")
        } else {
            users[email] = User(name, email)
            userCountText.text = "Users -> ${users.size}"
            nameField.text?.clear()
            emailField.text?.clear()
        }
    }

    private fun getUserInfo(email: String) = with(binding) {
        if (email.isEmpty()) {
            makeToast("Please enter your email")
            return
        }
        val user = users[email]
        if (user == null) {
            userNameContainer.text = ""
            emailContainer.text = ""
            userNotFoundText.visibility = View.VISIBLE
        } else {
            userNotFoundText.visibility = View.GONE
            emailContainer.text = email
            userNameContainer.text = user.name
        }
    }

    private fun isEmailValid(): Boolean = with(binding) {
        val email = emailField.text.toString()
        return if ((!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            makeToast("Invalid Email")
            false
        } else true
    }

    private fun EditText.getInputText() = this.text.toString().trim()

    private fun makeToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }
}
