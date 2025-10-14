package com.example.tbcacademy.screens

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.ActivityUserDashboardBinding
import com.example.tbcacademy.model.OperationType
import com.example.tbcacademy.model.User

class UserDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDashboardBinding
    private val userMap = mutableMapOf<String, User>()
    private val deletedUsers = mutableListOf<User>()

    private val formResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data ?: return@registerForActivityResult
        val operationType = data.getParcelableExtra(OPERATION_TYPE, OperationType::class.java)
            ?: return@registerForActivityResult
        val user = data.getParcelableExtra(USER, User::class.java)

        user?.let {
            when (operationType) {
                OperationType.ADD -> {
                    if (userMap.containsKey(it.email)) {
                        handleError(R.string.user_already_exists)
                    } else {
                        userMap[it.email] = it
                        handleSuccess(R.string.user_added_successfully)
                    }
                    showActiveUsers()
                }

                OperationType.REMOVE -> {
                    userMap.remove(it.email)
                    deletedUsers.add(it)
                    showActiveUsers()
                    showDeletedUsers()
                    handleSuccess(R.string.user_removed)
                }

                OperationType.UPDATE -> {
                    userMap[it.email] = it
                    showActiveUsers()
                    handleSuccess(R.string.user_updated_successfully)
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

        showActiveUsers()
        showDeletedUsers()
        setListeners()
    }

    private fun setListeners() {
        setAddButtonClickListener()
        setUpdateUserClickListener()
    }

    private fun setAddButtonClickListener() = with(binding) {
        btnAddUser.setOnClickListener {
            val intent = Intent(this@UserDashboardActivity, UserFormActivity::class.java)
            intent.putExtra(OPERATION_TYPE, OperationType.ADD)
            formResultLauncher.launch(intent)
        }
    }

    private fun setUpdateUserClickListener() = with(binding) {
        btnUpdateUser.setOnClickListener {
            if (userMap.isEmpty()) {
                handleError(R.string.user_list_is_empty)
                return@setOnClickListener
            }
            val randomUser = userMap.values.random()
            val intent = Intent(this@UserDashboardActivity, UserFormActivity::class.java)
            intent.putExtra(OPERATION_TYPE, OperationType.UPDATE)
            intent.putExtra(USER, randomUser)
            formResultLauncher.launch(intent)
        }
    }

    private fun showActiveUsers() = with(binding) {
        twActiveUsers.text = getString(R.string.active_users, userMap.size)
    }

    private fun showDeletedUsers() = with(binding) {
        twDeletedUsers.text = getString(R.string.deleted_users, deletedUsers.size)
    }

    private fun handleSuccess(@StringRes resId: Int) = with(binding) {
        twStatusMessage.text = getString(resId)
        twStatusMessage.setTextColor(Color.GREEN)
    }

    private fun handleError(@StringRes resId: Int) = with(binding) {
        twStatusMessage.text = getString(resId)
        twStatusMessage.setTextColor(Color.RED)
    }

    companion object {
        const val OPERATION_TYPE = "operation_type"
        const val USER = "user"
    }
}
