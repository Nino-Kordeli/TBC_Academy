package com.example.tbcacademy.presentation.screens.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentProfileBinding
import com.example.tbcacademy.presentation.screens.profile.vm.ProfileViewModel
import com.example.tbcacademy.presentation.viewmodel.ViewModelFactory
import com.example.tbcacademy.utils.extensions.showSnackBar
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by lazy {
        ViewModelFactory.createProfileViewModelFactory(requireContext())
            .create(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener { viewModel.logout() }
        observeState()
        observeEvents()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.tvProfileUserEmail.text = state.email
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect {
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                binding.root.showSnackBar("Logged out successfully")
            }
        }
    }
}