package com.example.tbcacademy.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentHomeBinding
import com.example.tbcacademy.presentation.screens.home.adapter.UserAdapter
import com.example.tbcacademy.presentation.screens.home.vm.HomeEffect
import com.example.tbcacademy.presentation.screens.home.vm.HomeViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter = UserAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUserList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        binding.rvUserList.adapter = adapter
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        observeState()
        observeEffects()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.isLoading) {
                        binding.root.showSnackBar(getString(R.string.loading_users))
                    }

                    adapter.submitList(state.users)

                    if (state.users.isEmpty() && !state.isLoading) {
                        binding.root.showSnackBar(getString(R.string.no_users_found))
                    }
                }
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    if (effect is HomeEffect.ShowError) {
                        binding.root.showSnackBar(effect.message)
                    }
                }
            }
        }
    }
}