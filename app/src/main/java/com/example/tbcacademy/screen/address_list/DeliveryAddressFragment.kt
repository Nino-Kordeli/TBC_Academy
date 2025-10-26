package com.example.tbcacademy.screen.address_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentDeliveryAddressBinding
import com.example.tbcacademy.screen.address_add.model.Address
import com.example.tbcacademy.screen.address_list.adapter.DeliveryAddressAdapter
import com.example.tbcacademy.screen.address_list.viewmodel.AddressViewModel

class DeliveryAddressFragment :
    BaseFragment<FragmentDeliveryAddressBinding>() {

    private lateinit var viewModel: AddressViewModel
    private lateinit var adapter: DeliveryAddressAdapter
    private var selectedAddressId: Int? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDeliveryAddressBinding {
        return FragmentDeliveryAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[AddressViewModel::class.java]

        setupRecyclerView()
        observeAddresses()
        setupAddButton()
        setupBackButton()
    }

    private fun setupRecyclerView() = with(binding) {
        adapter = DeliveryAddressAdapter(
            onAddressSelected = { address ->
                selectedAddressId = address.id
            },
            onEditClick = { address ->
                navigateToEditAddress(address)
            },
            onAddressLongClick = { address ->
                viewModel.deleteAddress(address)
            }
        )

        rvAddresses.layoutManager = LinearLayoutManager(requireContext())
        rvAddresses.adapter = adapter
    }

    private fun observeAddresses() {
        viewModel.addressList.observe(viewLifecycleOwner) { addresses ->
            adapter.submitList(addresses.toList())
        }
    }

    private fun navigateToEditAddress(address: Address) {
        val action = DeliveryAddressFragmentDirections
            .actionDeliveryAddressFragmentToAddAddressFragment(address, true)
        findNavController().navigate(action)
    }

    private fun setupAddButton() = with(binding) {
        btnAddNewAddress.setOnClickListener {
            findNavController().navigate(
                DeliveryAddressFragmentDirections
                    .actionDeliveryAddressFragmentToAddAddressFragment(null, false)
            )
        }
    }

    private fun setupBackButton() = with(binding) {
        ivBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
