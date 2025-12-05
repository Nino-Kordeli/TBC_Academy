package com.example.tbcacademy.presentation.users.fragment

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentUsersBinding
import com.example.tbcacademy.presentation.users.adapter.UsersAdapter
import com.example.tbcacademy.presentation.users.contract.UsersEvent
import com.example.tbcacademy.presentation.users.contract.UsersSideEffects
import com.example.tbcacademy.presentation.users.vm.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()
    private val usersAdapter by lazy { UsersAdapter() }

    override fun bind() {
        setupRecyclerView()
        setupListeners()
        observeState()
        observeSideEffects()

        viewModel.onEvent(UsersEvent.FetchUsers)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onEvent(UsersEvent.FetchUsers)
        }
    }

    private fun observeState() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    swipeRefreshLayout.isRefreshing = state.isLoading

                    tvConnectivityStatus.apply {
                        text =
                            if (state.isOnline) context.getString(R.string.you_are_online) else context.getString(
                                R.string.you_are_offline
                            )
                        isVisible = true
                    }

                    state.users?.let { usersList ->
                        usersAdapter.submitList(usersList)
                        recyclerViewUsers.isVisible = usersList.isNotEmpty()
                        tvEmptyState.isVisible = usersList.isEmpty()
                    }
                }
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collectLatest { sideEffect ->
                    when (sideEffect) {
                        is UsersSideEffects.ShowError -> {
                            val message = when {
                                !sideEffect.message.isNullOrEmpty() -> sideEffect.message
                                sideEffect.messageResId != null -> getString(sideEffect.messageResId)
                                else -> getString(R.string.unknown_error)
                            }

                            if (message.isNotEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        is UsersSideEffects.ShowSuccess -> {
                            val message = getString(sideEffect.messageResId)

                            if (message.isNotEmpty()) {
                                Toast.makeText(
                                    requireContext(),
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}