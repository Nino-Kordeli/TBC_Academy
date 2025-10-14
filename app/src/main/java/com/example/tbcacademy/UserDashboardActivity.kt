package com.example.tbcacademy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityUserDashboardBinding
import kotlin.collections.containsKey

class UserDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDashboardBinding
    private val userMap = mutableMapOf<String, User>()
    private val deletedUsers = mutableMapOf<String, User>()

    private val formResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data ?: return@registerForActivityResult
        val action = data.getStringExtra("action") ?: return@registerForActivityResult
        val user = data.getParcelableExtra("user", User::class.java)

        user?.let {
            when (action) {
                "add" -> {
                    if (userMap.containsKey(it.email)) {
                        handleError(R.string.user_already_exists)
                    } else {
                        userMap[it.email] = it
                        handleSuccess(R.string.user_added_successfully)
                    }
                    showActiveUsers()
                }

                "update" -> {
                    userMap[it.email] = it
                    showActiveUsers()
                    showMessage("User updated successfully")
                }

                "remove" -> {
                    userMap.remove(it.email)
                    deletedUsers[it.email] = it
                    showActiveUsers()
                    showDeletedUsers()
                    showMessage("User removed successfully")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
    }

    private fun setListeners() {
        setAddButtonClickListener()
        setUpdateUserClickListener()
    }

    private fun setAddButtonClickListener() = with(binding) {
        btnAddUser.setOnClickListener {
            val intent = Intent(this@UserDashboardActivity, UserFormActivity::class.java)
            intent.putExtra("mode", "add")
            formResultLauncher.launch(intent)
        }
    }

    private fun setUpdateUserClickListener() = with(binding) {
        btnUpdateUser.setOnClickListener {
            if (userMap.isEmpty()) {
                showMessage(getString(R.string.user_list_is_empty))
                return@setOnClickListener
            }
            val randomUser = userMap.values.random()
            val intent = Intent(this@UserDashboardActivity, UserFormActivity::class.java)
            intent.putExtra("mode", "update")
            intent.putExtra("user", randomUser)
            formResultLauncher.launch(intent)
        }
    }

    private fun showActiveUsers() = with(binding) {
        twActiveUsers.text = getString(R.string.active_users, userMap.size)
    }

    private fun showDeletedUsers() = with(binding) {
        twDeletedUsers.text = getString(R.string.deleted_users, deletedUsers.size)
    }

    private fun showMessage(message: String) = with(binding) {
        twResultMessage.text = message
    }

    private fun handleSuccess(@StringRes resId: Int) = with(binding) {
        twResultMessage.text = getString(resId)
        twResultMessage.setTextColor(Color.GREEN)
    }

    private fun handleError(@StringRes resId: Int) = with(binding) {
        twResultMessage.text = getString(resId)
        twResultMessage.setTextColor(Color.RED)
    }

}
