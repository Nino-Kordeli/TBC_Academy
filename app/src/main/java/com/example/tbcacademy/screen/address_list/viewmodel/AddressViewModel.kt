package com.example.tbcacademy.screen.address_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tbcacademy.screen.address_add.model.Address

class AddressViewModel : ViewModel() {

    val addressList = MutableLiveData<MutableList<Address>>(
        mutableListOf(
            Address(0, "My Office", "SBI Building, street 3, Software Park"),
            Address(1, "My Home", "SBI Building, street 3, Software Park")
        )
    )

    fun addAddress(address: Address) {
        val current = addressList.value ?: mutableListOf()
        current.add(0, address)
        addressList.value = current
    }

    fun editAddress(updated: Address) {
        val current = addressList.value ?: mutableListOf()
        val index = current.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            current[index] = updated
            addressList.value = current
        }
    }

    fun deleteAddress(address: Address) {
        val current = addressList.value ?: mutableListOf()
        current.remove(address)
        addressList.value = current
    }
}