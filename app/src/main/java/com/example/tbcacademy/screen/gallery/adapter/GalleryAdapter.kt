package com.example.tbcacademy.screen.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.screen.gallery.adapter.model.GalleryItem
import com.example.tbcacademy.databinding.ItemGalleryBinding

class GalleryAdapter :
    ListAdapter<GalleryItem, GalleryAdapter.GalleryViewHolder>(GalleryDiffCallback()) {

    inner class GalleryViewHolder(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GalleryItem) = with(binding) {
            ivModelGirl.setImageResource(item.imageRes)
            tvItemTitle.text = item.title
            tvItemPrice.text = item.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class GalleryDiffCallback : DiffUtil.ItemCallback<GalleryItem>() {
    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem == newItem
    }
}
