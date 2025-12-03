package com.example.tbcacademy.presentation.lock_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.NumberItemBinding
import com.example.tbcacademy.presentation.lock_screen.model.KeypadItem

class NumberAdapter(
    private val onClick: (KeypadItem) -> Unit
) : ListAdapter<KeypadItem, NumberAdapter.NumberViewHolder>(DiffCallback()) {

    inner class NumberViewHolder(
        private val binding: NumberItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KeypadItem) = with(binding) {
            when (item) {
                is KeypadItem.Number -> {
                    numberText.text = item.number
                    background.setImageResource(R.drawable.number_background)
                }
                KeypadItem.Delete -> {
                    numberText.text = ""
                    background.setImageResource(R.drawable.ic_delete)
                }
                KeypadItem.Fingerprint -> {
                    numberText.text = ""
                    background.setImageResource(R.drawable.ic_touch_id)
                }
            }

            root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = NumberItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<KeypadItem>() {
        override fun areItemsTheSame(oldItem: KeypadItem, newItem: KeypadItem) = oldItem == newItem
        override fun areContentsTheSame(oldItem: KeypadItem, newItem: KeypadItem) = oldItem == newItem
    }
}
