package com.example.tbcacademy.screens.dashboard

import com.example.tbcacademy.common.BaseFragment
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.FragmentUserDashboardBinding
import com.example.tbcacademy.model.OperationType
import com.example.tbcacademy.model.User
import com.example.tbcacademy.screens.dashboard.adapter.UserAdapter

class UserDashboardFragment : BaseFragment<FragmentUserDashboardBinding>() {

    private val userMap = mutableMapOf<String, User>()
    private val deletedUsers = mutableListOf<User>()
    private lateinit var adapter: UserAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserDashboardBinding {
        return FragmentUserDashboardBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setListeners()
        showActiveUsers()
        showDeletedUsers()
        listenForFormResults()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setRecyclerView() = with(binding) {
        adapter = UserAdapter { user ->
            findNavController().navigate(
                UserDashboardFragmentDirections.actionUserDashboardFragmentToUserFormFragment(
                    operationType = OperationType.UPDATE,
                    user = user
                )
            )
        }
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setListeners() = with(binding) {
        btnAddUser.setOnClickListener {
            findNavController().navigate(
                UserDashboardFragmentDirections.actionUserDashboardFragmentToUserFormFragment(
                    operationType = OperationType.ADD,
                    user = null
                )
            )
        }

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

    private fun listenForFormResults() {
        setFragmentResultListener("userFormResult") { _, bundle ->
            val operationType = bundle.getParcelable("operationType", OperationType::class.java)
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
            }
            OperationType.REMOVE -> {
                userMap.remove(user.email)
                deletedUsers.add(user)
                handleSuccess(R.string.user_removed)
            }
            OperationType.UPDATE -> {
                userMap[user.email] = user
                handleSuccess(R.string.user_updated_successfully)
            }
        }
        adapter.submitList(userMap.values.toList())
        showActiveUsers()
        showDeletedUsers()
    }

    private fun showActiveUsers() {
        binding.twActiveUsers.text = getString(R.string.active_users, userMap.size)
    }

    private fun showDeletedUsers() {
        binding.twDeletedUsers.text = getString(R.string.deleted_users, deletedUsers.size)
    }

    private fun handleSuccess(@StringRes resId: Int) {
        binding.twStatusMessage.text = getString(resId)
        binding.twStatusMessage.setTextColor(Color.GREEN)
    }

    private fun handleError(@StringRes resId: Int) {
        binding.twStatusMessage.text = getString(resId)
        binding.twStatusMessage.setTextColor(Color.RED)
    }
}
