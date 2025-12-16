package com.example.tbcacademy.presentation.home.adapter

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.RecipeItemBinding
import com.example.tbcacademy.presentation.model.RecipeDetailsUi
import com.example.tbcacademy.presentation.model.RecipeUi

class TrendingRecipesAdapter(
    private val onRecipeClick: (Int) -> Unit,
    private val onFavoriteClick: (Int) -> Unit,
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

    inner class RecipeViewHolder(
        private val binding: RecipeItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeUi) = with(binding) {

            recipeName.text = item.name

            Glide.with(ivRecipeImage.context)
                .load(item.imageUrl)
                .into(ivRecipeImage)

            d("askndaslas", item.isFavourite.toString())
            ivBookmark.setImageResource(
                if (item.isFavourite)
                    R.drawable.ic_favorited_bookmark
                else
                    R.drawable.ic_bookmark_white
            )

            root.setOnClickListener {
                onRecipeClick(item.id)
            }

            ivBookmark.setOnClickListener {
                onFavoriteClick(item.id)
            }
        }
    }

    private class RecipeDiffCallback : DiffUtil.ItemCallback<RecipeUi>() {
        override fun areItemsTheSame(oldItem: RecipeUi, newItem: RecipeUi): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipeUi, newItem: RecipeUi): Boolean =
            oldItem == newItem
    }
}