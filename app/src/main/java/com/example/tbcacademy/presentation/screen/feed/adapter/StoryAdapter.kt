package com.example.tbcacademy.presentation.screen.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcacademy.databinding.CardItemBinding
import com.example.tbcacademy.domain.model.Story

class StoryAdapter :
    ListAdapter<Story, StoryAdapter.StoryViewHolder>(DiffCallback) {

    inner class StoryViewHolder(val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvTitle.text = item.title
            storyImage.load(item.cover)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(a: Story, b: Story) = a.title == b.title
        override fun areContentsTheSame(a: Story, b: Story) = a == b
    }
}

