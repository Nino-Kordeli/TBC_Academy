package com.example.tbcacademy.presentation.lock_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentLockScreenBinding
import com.example.tbcacademy.presentation.lock_screen.adapter.DotAdapter
import com.example.tbcacademy.presentation.lock_screen.adapter.NumberAdapter
import com.example.tbcacademy.presentation.lock_screen.model.KeypadItem
import com.example.tbcacademy.presentation.lock_screen.vm.LockScreenViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LockScreenFragment :
    BaseFragment<FragmentLockScreenBinding, LockScreenViewModel>() {

    override val viewModel: LockScreenViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLockScreenBinding.inflate(inflater, container, false)

    private lateinit var dotAdapter: DotAdapter
    private lateinit var numberAdapter: NumberAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDots()
        setupKeypad()
        observeFlows()
    }

    private fun setupDots() {
        dotAdapter = DotAdapter()
        binding.rvPasswordDots.apply {
            adapter = dotAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            itemAnimator = null
        }
        dotAdapter.updateDots(0)
    }

    private fun setupKeypad() {
        numberAdapter = NumberAdapter { item ->
            when (item) {
                is KeypadItem.Number -> viewModel.addDigit(item.number)
                KeypadItem.Delete -> viewModel.removeDigit()
                KeypadItem.Fingerprint -> ""
            }
        }

        binding.rvNumbers.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = numberAdapter

            itemAnimator = null
        }

        val keypadItems = listOf(
            KeypadItem.Number("1"),
            KeypadItem.Number("2"),
            KeypadItem.Number("3"),
            KeypadItem.Number("4"),
            KeypadItem.Number("5"),
            KeypadItem.Number("6"),
            KeypadItem.Number("7"),
            KeypadItem.Number("8"),
            KeypadItem.Number("9"),
            KeypadItem.Fingerprint,
            KeypadItem.Number("0"),
            KeypadItem.Delete
        )

        numberAdapter.submitList(keypadItems)
    }

    private fun observeFlows() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {

                    launch {
                        viewModel.dots.collect { dotStates ->
                            dotAdapter.updateDots(dotStates.count { it })
                        }
                    }

                    launch {
                        viewModel.status.collect { status ->
                            when (status) {
                                "success" -> {
                                    root.showSnackBar("Success")
                                    viewModel.resetStatus()
                                }
                                "incorrect" -> {
                                    root.showSnackBar("Incorrect password")
                                    viewModel.resetStatus()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}