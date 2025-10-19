package com.example.tbcacademy.screens

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.FragmentUserDashboardBinding
import com.example.tbcacademy.model.OperationType
import com.example.tbcacademy.model.User

class UserDashboardFragment : Fragment() {

    private var _binding: FragmentUserDashboardBinding? = null
    private val binding get() = _binding!!

    private val userMap = mutableMapOf<String, User>()
    private val deletedUsers = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDashboardBinding.inflate(inflater, container, false)

        setListeners()
        showActiveUsers()
        showDeletedUsers()
        listenForFormResults()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        return binding.root
    }

    private fun setListeners() = with(binding) {
        setBtnAddUserListener()
        setBtnUpdateUser()
    }

    private fun listenForFormResults() {
        setFragmentResultListener("userFormResult") { _, bundle ->
            val operationType = bundle.getSerializable("operationType", OperationType::class.java)
                ?: return@setFragmentResultListener
            val user = bundle.getParcelable("user", User::class.java)
                ?: return@setFragmentResultListener
            handleFormResult(operationType, user)
        }
    }

    fun handleFormResult(operationType: OperationType, user: User) {
        when (operationType) {
            OperationType.ADD -> {
                if (userMap.containsKey(user.email)) handleError(R.string.user_already_exists)
                else {
                    userMap[user.email] = user
                    handleSuccess(R.string.user_added_successfully)
                }
                showActiveUsers()
            }

            OperationType.REMOVE -> {
                userMap.remove(user.email)
                deletedUsers.add(user)
                showActiveUsers()
                showDeletedUsers()
                handleSuccess(R.string.user_removed)
            }

            OperationType.UPDATE -> {
                userMap[user.email] = user
                showActiveUsers()
                handleSuccess(R.string.user_updated_successfully)
            }
        }
    }

    private fun setBtnAddUserListener() = with(binding) {
        btnAddUser.setOnClickListener {
            findNavController().navigate(
                UserDashboardFragmentDirections.actionUserDashboardFragmentToUserFormFragment(
                    operationType = OperationType.ADD,
                    user = null
                )
            )
        }
    }

    private fun setBtnUpdateUser() = with(binding) {
        btnUpdateUser.setOnClickListener {
            if (userMap.isEmpty()) {
                handleError(R.string.user_list_is_empty)
                return@setOnClickListener
            }
            val randomUser = userMap.values.random()
            findNavController().navigate(
                UserDashboardFragmentDirections.actionUserDashboardFragmentToUserFormFragment(
                    operationType = OperationType.UPDATE,
                    user = randomUser
                )
            )
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
