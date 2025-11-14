package com.example.tbcacademy.presentation.screens.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentProfileBinding
import com.example.tbcacademy.presentation.screens.profile.vm.ProfileViewModel
import com.example.tbcacademy.presentation.screens.profile.vm.ProfileViewModelFactory
import kotlinx.coroutines.launch


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(requireContext())
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProfile()
        setListener()
    }

    private fun observeProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvUserName.text = state.username ?: "Username"
                    binding.tvEmail.text = state.email ?: "Email"
                }
            }
        }
    }

    private fun setListener() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(
                R.id.action_profileFragment_to_welcomeFragment,
                null,
                androidx.navigation.navOptions {
                    popUpTo(R.id.nav_graph) { inclusive = true }
                }
            )
        }
    }
}