package com.example.tbcacademy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityRegistrationCompleteBinding

class RegistrationCompleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrationCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        displatUserInfo()
        setListeners()
    }

    private fun setListeners() = with(binding) {
        btnLogOut.setOnClickListener {
            val intent = Intent(this@RegistrationCompleteActivity, LoggedOutActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun displatUserInfo() = with(binding) {
        val email = intent.getStringExtra("email") ?: ""
        twEmail.text = email
    }
}