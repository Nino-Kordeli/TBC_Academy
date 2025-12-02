package com.example.tbcacademy.presentation.lock_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.lock_screen.model.KeypadItem

class NumberAdapter(
    private val onClick: (KeypadItem) -> Unit
) : ListAdapter<KeypadItem, NumberAdapter.NumberViewHolder>(DiffCallback()) {

    inner class NumberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberText: TextView = view.findViewById(R.id.numberText)
        val background: ImageView = view.findViewById(R.id.background)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.number_item, parent, false)
        return NumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val item = getItem(position)
        when (item) {
            is KeypadItem.Number -> {
                holder.numberText.text = item.number
                holder.background.setImageResource(R.drawable.number_background)
            }
            KeypadItem.Delete -> {
                holder.numberText.text = ""
                holder.background.setImageResource(R.drawable.ic_delete)
            }
            KeypadItem.Fingerprint -> {
                holder.numberText.text = ""
                holder.background.setImageResource(R.drawable.ic_touch_id)
            }
        }
        holder.itemView.setOnClickListener { onClick(item) }
    }

    class DiffCallback : DiffUtil.ItemCallback<KeypadItem>() {
        override fun areItemsTheSame(oldItem: KeypadItem, newItem: KeypadItem) = oldItem == newItem
        override fun areContentsTheSame(oldItem: KeypadItem, newItem: KeypadItem) = oldItem == newItem
    }
}
