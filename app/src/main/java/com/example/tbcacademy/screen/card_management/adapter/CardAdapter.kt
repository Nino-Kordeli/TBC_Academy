package com.example.tbcacademy.screen.card_management.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.databinding.CardItemBinding
import com.example.tbcacademy.screen.card_management.model.Card

class CardAdapter(
    private val onLongClick: (Card) -> Unit
) : ListAdapter<Card, CardAdapter.CardViewHolder>(CardDiffCallback()) {

    inner class CardViewHolder(val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = getItem(position)

        with(holder.binding) {
            tvCardHolderName.text = card.cardHolder
            tvCardNumber.text = card.cardNumber
            tvValidThrough.text = card.expirationDate
            ivCardImage.setImageResource(card.backgroundRes)

            root.setOnLongClickListener {
                onLongClick(card)
                true
            }
        }
    }

    class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Card, newItem: Card) =
            oldItem == newItem
    }
}