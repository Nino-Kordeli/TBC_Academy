package com.example.tbcacademy.presentation.lock_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R

class DotAdapter(private val totalDots: Int = 4) :
    RecyclerView.Adapter<DotAdapter.DotViewHolder>() {

    private var filledCount = 0

    inner class DotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dot: View = view.findViewById(R.id.dot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dot_item, parent, false)
        return DotViewHolder(view)
    }

    override fun onBindViewHolder(holder: DotViewHolder, position: Int) {
        holder.dot.setBackgroundResource(
            if (position < filledCount) R.drawable.circle_filled else R.drawable.circle_empty
        )
    }

    override fun getItemCount() = totalDots

    fun updateDots(newFilledCount: Int) {
        val oldFilledCount = filledCount
        filledCount = newFilledCount.coerceIn(0, totalDots)

        if (oldFilledCount != filledCount) {
            notifyItemRangeChanged(0, totalDots)
        }
    }
}