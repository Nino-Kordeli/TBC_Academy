package com.example.tbcacademy.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentHomeBinding
import com.example.tbcacademy.presentation.screens.home.adapter.UserAdapter
import com.example.tbcacademy.presentation.screens.home.vm.HomeUiState
import com.example.tbcacademy.presentation.screens.home.vm.HomeViewModel
import com.example.tbcacademy.presentation.viewmodel.ViewModelFactory
import com.example.tbcacademy.utils.extensions.showSnackBar
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by lazy {
        ViewModelFactory.createHomeViewModelFactory(requireContext())
            .create(HomeViewModel::class.java)
    }

    private val adapter = UserAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUserList.adapter = adapter
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HomeUiState.Loading -> binding.root.showSnackBar("Loading users...")
                    is HomeUiState.Success -> adapter.submitList(state.users)
                    is HomeUiState.Error -> binding.root.showSnackBar(state.message)
                }
            }
        }
    }
}