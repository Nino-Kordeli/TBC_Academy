package com.example.tbcacademy.screen.address_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentAddAddressBinding
import com.example.tbcacademy.screen.address_add.model.Address
import com.example.tbcacademy.screen.address_list.viewmodel.AddressViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.example.tbcacademy.utils.extensions.trimmedTextValue

class AddAddressFragment : BaseFragment<FragmentAddAddressBinding>() {

    private lateinit var viewModel: AddressViewModel
    private var editingAddress: Address? = null
    private var isEditMode = false

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddAddressBinding {
        return FragmentAddAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[AddressViewModel::class.java]
        receiveArguments()
        setupUI()
        setSaveButtonListener()
        setupBackButton()
    }

    private fun receiveArguments() {
        arguments?.let {
            val args = AddAddressFragmentArgs.fromBundle(it)
            editingAddress = args.address
            isEditMode = args.isEdit
        }
    }

    private fun setupUI() = with(binding) {
        if (isEditMode && editingAddress != null) {
            etAddressTitleField.setText(editingAddress!!.title)
            etAddressDetailsField.setText(editingAddress!!.description)
            tvAddFragmentTitleText.text = getString(R.string.edit_address)
            btnAddAddressDetails.text = getString(R.string.save)
        } else {
            tvAddFragmentTitleText.text = getString(R.string.add_new_address)
            btnAddAddressDetails.text = getString(R.string.add)
        }
    }

    private fun setupBackButton() = with(binding) {
        ivBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setSaveButtonListener() = with(binding) {
        btnAddAddressDetails.setOnClickListener {
            val title = etAddressTitleField.trimmedTextValue()
            val details = etAddressDetailsField.trimmedTextValue()

            if (title.isEmpty() || details.isEmpty()) {
                root.showSnackBar("Please fill out all fields")
                return@setOnClickListener
            }

            val address = if (isEditMode && editingAddress != null) {
                Address(editingAddress!!.id, title, details)
            } else {
                val uniqueId = System.currentTimeMillis().toInt()
                Address(uniqueId, title, details)
            }

            if (isEditMode) {
                viewModel.editAddress(address)
            } else {
                viewModel.addAddress(address)
            }

            findNavController().navigateUp()
        }
    }
}
