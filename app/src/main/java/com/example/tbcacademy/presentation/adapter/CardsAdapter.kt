package com.example.tbcacademy.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcacademy.R
import com.example.tbcacademy.data.model.Cards
import com.example.tbcacademy.databinding.CardItemBinding

class CardsAdapter : ListAdapter<Cards, CardsAdapter.CardViewHolder>(CardsDiffCallback()) {

    inner class CardViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Cards) = with(binding) {
            tvLocationName.text = card.location
            tvLocationTitle.text = card.title
            numbers.text = "${card.altitudeM}"
            tvPrice.text = "$${card.stars * 20}"

            ivImage.load(card.image) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }

            ratingBar.rating = card.stars.toFloat()
            ratingBar.stepSize = 0.5f
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CardsDiffCallback : DiffUtil.ItemCallback<Cards>() {
    override fun areItemsTheSame(oldItem: Cards, newItem: Cards) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Cards, newItem: Cards) = oldItem == newItem
}
