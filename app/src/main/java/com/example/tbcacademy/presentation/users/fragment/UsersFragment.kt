package com.example.tbcacademy.presentation.users.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentUsersBinding
import com.example.tbcacademy.presentation.users.contract.UsersEvent
import com.example.tbcacademy.presentation.users.vm.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel: UsersViewModel by viewModels()

    override fun bind() {
        viewModel.onEvent(UsersEvent.FetchUsers)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    state.users?.let { usersList ->
                    }
                }
            }
        }
    }
}