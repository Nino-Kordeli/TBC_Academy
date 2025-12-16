package com.example.tbcacademy.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcacademy.databinding.RecipeItemBinding
import com.example.tbcacademy.presentation.model.RecipeUi

class TrendingRecipesAdapter(
    private val onRecipeClick: (Int) -> Unit,
) : ListAdapter<RecipeUi, TrendingRecipesAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeUi) {
            binding.recipeName.text = recipe.name
            Glide.with(binding.ivRecipeImage.context)
                .load(recipe.imageUrl)
                .into(binding.ivRecipeImage)

            binding.root.setOnClickListener {
                onRecipeClick(recipe.id)
            }
        }
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<RecipeUi>() {
        override fun areItemsTheSame(oldItem: RecipeUi, newItem: RecipeUi) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipeUi, newItem: RecipeUi) =
            oldItem == newItem
    }
}
