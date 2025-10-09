package com.example.tbcacademy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityLoggedOutBinding

class LoggedOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoggedOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoggedOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()

    }

    private fun setListeners() = with(binding) {
        btnRegister.setOnClickListener {
            val intent = Intent(this@LoggedOutActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
        btnLogIn.setOnClickListener {
            val intent = Intent(this@LoggedOutActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}