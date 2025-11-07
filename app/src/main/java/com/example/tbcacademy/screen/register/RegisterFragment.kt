package com.example.tbcacademy.screen.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentRegisterBinding
import com.example.tbcacademy.screen.register.adapter.FieldsAdapter
import com.example.tbcacademy.screen.register.vm.RegisterViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    private val adapter by lazy {
        FieldsAdapter { fieldId, value ->
            viewModel.updateFieldValue(fieldId, value)
        }
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentRegisterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFields.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFields.adapter = adapter

        viewModel.loadFieldsFromResource(R.raw.fields)

        lifecycleScope.launch {
            viewModel.fields.collect { fieldList ->
                adapter.submitList(fieldList)
            }
        }


        binding.btnRegister.setOnClickListener {
            val errors = viewModel.validateFields()
            if (errors.isNotEmpty()) {
                binding.root.showSnackBar(errors.joinToString("\n"))
            } else {
                val result = viewModel.collectData()
                println(result)
                binding.root.showSnackBar(getString(R.string.registration_successful_))
            }
        }
    }
}
