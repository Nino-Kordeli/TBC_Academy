package com.example.tbcacademy.presentation.users.fragment

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
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

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    binding.swipeRefreshLayout.isRefreshing = state.isLoading

                    binding.tvConnectivityStatus.apply {
                        text = if (state.isOnline) "You are online." else "You are offline."
                        isVisible = true
                    }

                    state.users?.let { usersList ->
                        usersAdapter.submitList(usersList)
                        binding.recyclerViewUsers.isVisible = usersList.isNotEmpty()
                        binding.tvEmptyState.isVisible = usersList.isEmpty()
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
                            Toast.makeText(
                                requireContext(),
                                sideEffect.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is UsersSideEffects.ShowSuccess -> {
                            Toast.makeText(
                                requireContext(),
                                sideEffect.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}