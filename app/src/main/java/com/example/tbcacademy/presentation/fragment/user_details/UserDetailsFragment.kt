package com.example.tbcacademy.presentation.fragment.user_details

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentUserDetailsBinding
import com.example.tbcacademy.presentation.fragment.user_details.contract.UserDetailEvent
import com.example.tbcacademy.presentation.fragment.user_details.contract.UserDetailSideEffect
import com.example.tbcacademy.presentation.fragment.user_details.vm.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsFragment :
    BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {

    private val viewModel: UserDetailsViewModel by viewModels()

    override fun bind() {
        setupListeners()
        observeState()
        observeSideEffects()
    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.onEvent(UserDetailEvent.Logout)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.tvUserEmail.text = "Welcome, ${state.userEmail}!"
                    binding.btnLogout.isEnabled = !state.loading
                }
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        is UserDetailSideEffect.NavigateToWelcome -> {
                            findNavController().navigate(R.id.action_userDetailsFragment_to_loginFragment)
                        }

                        is UserDetailSideEffect.ShowToast -> {
                            Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}