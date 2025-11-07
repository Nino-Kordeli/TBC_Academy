package com.example.tbcacademy.screen.card_management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.databinding.PaymentMethodItemBinding

class CardPaymentMethodAdapter :
    ListAdapter<Int, CardPaymentMethodAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PaymentMethodItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageRes = getItem(position)
        holder.binding.ivPaymentMethod.setImageResource(imageRes)
    }

    class ViewHolder(val binding: PaymentMethodItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    object DiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(old: Int, new: Int) = old == new
        override fun areContentsTheSame(old: Int, new: Int) = old == new
    }
}