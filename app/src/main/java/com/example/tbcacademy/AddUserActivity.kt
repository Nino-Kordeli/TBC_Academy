package com.example.tbcacademy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityAddUserBinding
import com.example.tbcacademy.utils.ui.showSnackBar
import com.example.tbcacademy.utils.ui.trimmedTextValue

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListener()
    }

    private fun setListener() {
        setAddButtonListener()
        setCancelButtonListener()
    }

    private fun setAddButtonListener() = with(binding) {
        btnFieldButtonAdd.setOnClickListener {
            etFirstnameField.trimmedTextValue().takeIf { it.isNotEmpty() }?.let { firstName ->
                etLastnameField.trimmedTextValue().takeIf { it.isNotEmpty() }?.let { lastName ->

                    val email = etEmailField.trimmedTextValue().ifEmpty { null }
                    val address = etAddressField.trimmedTextValue().ifEmpty { null }
                    val desc = etDesc.trimmedTextValue().ifEmpty { null }
                    val birthday = System.currentTimeMillis()

                    val newUser = User(
                        id = (0..1000).random(),
                        firstName = firstName,
                        lastName = lastName,
                        birthday = birthday,
                        address = address ?: "",
                        email = email ?: "",
                        desc = desc
                    )

                    Intent().apply {
                        putExtra("newUser", newUser)
                        putExtra("searchText", "$firstName $lastName")
                    }.also { resultIntent ->
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }

                } ?: btnFieldButtonAdd.showSnackBar(getString(R.string.lastname_is_required))
            } ?: btnFieldButtonAdd.showSnackBar(getString(R.string.firstname_is_required))
        }
    }

    private fun setCancelButtonListener() = with(binding) {
        btnFieldButtonCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
