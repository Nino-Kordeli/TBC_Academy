package com.example.tbcacademy.screen.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.screen.gallery.adapter.model.CategoryItem
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.CategoryItemBinding
import com.example.tbcacademy.utils.extensions.hide
import com.example.tbcacademy.utils.extensions.show

class CategoryAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    private var selectedPosition = 0

    inner class CategoryViewHolder(
        val binding: CategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryItem, bindingAdapterPosition: Int) = with(binding) {
            if (category.emoji != null) {
                tvEmoji.text = category.emoji
                tvEmoji.show()
            } else {
                tvEmoji.hide()
            }

            tvButtonContainer.text = category.name

            val isSelected = bindingAdapterPosition == selectedPosition
            val backgroundRes = if (isSelected) {
                R.drawable.selected_category_shape
            } else {
                R.drawable.unselected_category_shape
            }
            val textColorRes = if (isSelected) {
                R.color.white
            } else {
                R.color.light_grey
            }

            categoryButton.apply {
                setBackgroundResource(backgroundRes)
                tvButtonContainer.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        textColorRes
                    )
                )
                setOnClickListener {
                    val previousPosition = selectedPosition
                    selectedPosition = bindingAdapterPosition

                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)

                    onClick(category.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
    override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean =
        oldItem == newItem
}
