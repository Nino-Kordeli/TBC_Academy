package com.example.tbcacademy

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val users = mutableListOf<User>()
    private var userCount = 0

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

        binding.addUserButton.setOnClickListener {
            areFieldsEmpty()
        }

        binding.getUserInfoButton.setOnClickListener {

        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }

    private fun areFieldsEmpty() {
        val name = binding.nameField.text.toString().trim()
        val email = binding.emailField.text.toString().trim()

        if (name.isEmpty() || email.isEmpty()) {
            makeToast("Fill out all the fields")
        } else {
            users.add(User(name, email))
            userCount++
            binding.userCount.text = "User Count -> $userCount"
            binding.nameField.setText("")
            binding.emailField.setText("")
        }
    }
}
