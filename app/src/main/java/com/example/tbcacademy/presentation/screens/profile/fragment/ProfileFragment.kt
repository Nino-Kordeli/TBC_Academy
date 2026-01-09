package com.example.tbcacademy.presentation.screens.profile.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.FragmentProfileBinding
import com.example.tbcacademy.presentation.common.BaseFragment
import com.example.tbcacademy.presentation.screens.profile.contract.ProfileEvent
import com.example.tbcacademy.presentation.screens.profile.vm.ProfileViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()
    override fun bind() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { }

        setupListeners()
        observeState()
        observeEffects()
    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            it.isEnabled = false
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.tvProfileUserEmail.text = state.email
                }
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {
                        ProfileEvent.LogoutSuccess -> {
                            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                            binding.root.showSnackBar(getString(R.string.logged_out_successfully_))
                        }
                    }
                }
            }
        }
    }
}