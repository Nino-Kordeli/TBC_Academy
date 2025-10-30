package com.example.tbcacademy.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.screen.model.DeliveryItem
import com.example.tbcacademy.databinding.OrdersItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DeliveryAdapter(
    private val onDetailsClick: (DeliveryItem) -> Unit
) : ListAdapter<DeliveryItem, DeliveryAdapter.DeliveryViewHolder>(DeliveryDiffCallback()) {

    private val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = OrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DeliveryViewHolder(
        private val binding: OrdersItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DeliveryItem) {
            binding.apply {
                tvOrderId.text = item.orderId
                tvPrice.text = item.price
                tvTrackingNumber.text = item.trackingNumber
                tvquantity.text = item.quantity
                tvStatusText.text = item.status.name
                tvDate.text = dateFormat.format(Date(item.date))

                btnDetails.setOnClickListener { onDetailsClick(item) }
            }
        }
    }

    private class DeliveryDiffCallback : DiffUtil.ItemCallback<DeliveryItem>() {
        override fun areItemsTheSame(oldItem: DeliveryItem, newItem: DeliveryItem) =
            oldItem.orderId == newItem.orderId

        override fun areContentsTheSame(oldItem: DeliveryItem, newItem: DeliveryItem) =
            oldItem == newItem
    }
}