package com.example.tbcacademy.screen.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentConfigurationBinding
import com.example.tbcacademy.utils.extensions.showSnackBar

class ConfigurationFragment : BaseFragment<FragmentConfigurationBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfigurationBinding {
        return FragmentConfigurationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartGame.setOnClickListener {
            val input = binding.etGridField.text.toString()
            val boardSize = input.toIntOrNull()
            if (boardSize == null || boardSize < 3 || boardSize > 5) {
                binding.root.showSnackBar("Enter valid number from 3 to 5")
                return@setOnClickListener
            }

            val action =
                ConfigurationFragmentDirections.actionConfigurationFragmentToGameFragment(boardSize)
            findNavController().navigate(action)
        }
    }
}