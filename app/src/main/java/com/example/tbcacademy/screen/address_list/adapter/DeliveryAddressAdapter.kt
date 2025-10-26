package com.example.tbcacademy.screen.address_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.databinding.AddressItemBinding
import com.example.tbcacademy.screen.address_add.model.Address

class DeliveryAddressAdapter(
    private val onAddressSelected: (Address) -> Unit,
    private val onEditClick: (Address) -> Unit,
    private val onAddressLongClick: (Address) -> Unit
) : ListAdapter<Address, DeliveryAddressAdapter.AddressViewHolder>(DiffCallback()) {

    var selectedAddressId: Int? = null

    inner class AddressViewHolder(
        private val binding: AddressItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address) = with(binding) {
            tvAddressName.text = address.title
            tvAddressDetails.text = address.description

            radioButton.isChecked = address.id == selectedAddressId
            tvEdit.visibility = if (address.id == selectedAddressId) View.VISIBLE else View.INVISIBLE

            radioButton.setOnClickListener {
                selectedAddressId = address.id
                onAddressSelected(address)
                notifyDataSetChanged()
            }

            tvEdit.setOnClickListener {
                onEditClick(address)
            }

            root.setOnLongClickListener {
                onAddressLongClick(address)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = AddressItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Address, newItem: Address) =
            oldItem == newItem
    }
}
