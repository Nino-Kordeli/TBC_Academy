package com.example.tbcacademy.presentation.lock_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.DotItemBinding

class DotAdapter(private val totalDots: Int = 4) :
    RecyclerView.Adapter<DotAdapter.DotViewHolder>() {

    private var filledCount = 0

    inner class DotViewHolder(
        private val binding: DotItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(isFilled: Boolean) {
            binding.dot.setBackgroundResource(
                if (isFilled) R.drawable.circle_filled else R.drawable.circle_empty
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotViewHolder {
        val binding = DotItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DotViewHolder, position: Int) {
        holder.bind(position < filledCount)
    }

    override fun getItemCount() = totalDots

    fun updateDots(newFilledCount: Int) {
        val oldFilled = filledCount
        filledCount = newFilledCount.coerceIn(0, totalDots)

        if (oldFilled != filledCount) {
            notifyItemRangeChanged(0, totalDots)
        }
    }
}
