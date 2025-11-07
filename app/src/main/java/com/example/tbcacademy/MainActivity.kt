package com.example.tbcacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tbcacademy.screen.register.RegisterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, RegisterFragment())
            .commit()
    }
}
