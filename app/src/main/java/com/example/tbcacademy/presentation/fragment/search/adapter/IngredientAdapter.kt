package com.example.tbcacademy.presentation.fragment.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcacademy.databinding.IngredientItemWithTitleBinding
import com.example.tbcacademy.presentation.model.IngredientUi

class IngredientAdapter(
    private val onClick: (IngredientUi) -> Unit
) : ListAdapter<IngredientUi, IngredientAdapter.IngredientViewHolder>(IngredientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = IngredientItemWithTitleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IngredientViewHolder(private val binding: IngredientItemWithTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: IngredientUi) {
            binding.tvIngredientName.text = ingredient.name
            Glide.with(binding.ivIngredient.context)
                .load(ingredient.imageUrl)
                .into(binding.ivIngredient)

            binding.root.setOnClickListener { onClick(ingredient) }
        }
    }

    class IngredientDiffCallback : DiffUtil.ItemCallback<IngredientUi>() {
        override fun areItemsTheSame(oldItem: IngredientUi, newItem: IngredientUi) =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: IngredientUi, newItem: IngredientUi) =
            oldItem == newItem
    }
}
