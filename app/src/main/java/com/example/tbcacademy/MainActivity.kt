package com.example.tbcacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tbcacademy.databinding.ActivityMainBinding
import com.example.tbcacademy.presentation.lock_screen.LockScreenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, LockScreenFragment())
                .commit()
        }
    }
}