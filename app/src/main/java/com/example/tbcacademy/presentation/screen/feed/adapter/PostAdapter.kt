package com.example.tbcacademy.presentation.screen.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tbcacademy.R
import com.example.tbcacademy.common.DateFormatter
import com.example.tbcacademy.common.extensions.hide
import com.example.tbcacademy.common.extensions.show
import com.example.tbcacademy.databinding.PostItemBinding
import com.example.tbcacademy.domain.model.Post

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallback) {

    inner class PostViewHolder(val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvUserName.text = root.context.getString(R.string.name, item.firstName, item.lastName)
            tvPostDesc.text = item.postDesc
            tvDateTime.text = DateFormatter.formatPostDate(item.postDate)
            tvComments.text = root.context.getString(R.string.comments, item.commentsCount)
            tvLikes.text = root.context.getString(R.string.likes, item.likesCount)

            ivUserIcon.load(item.avatar) { crossfade(true) }

            ivLeft.hide()
            ivRightTop.hide()
            ivRightBottom.hide()

            when (item.images.size) {
                1 -> {
                    ivLeft.show()
                    ivLeft.load(item.images[0]) { crossfade(true) }
                }

                2 -> {
                    ivLeft.show()
                    ivRightTop.show()
                    ivLeft.load(item.images[0]) { crossfade(true) }
                    ivRightTop.load(item.images[1]) { crossfade(true) }
                }

                3 -> {
                    ivLeft.show()
                    ivRightTop.show()
                    ivRightBottom.show()
                    ivLeft.load(item.images[0]) { crossfade(true) }
                    ivRightTop.load(item.images[1]) { crossfade(true) }
                    ivRightBottom.load(item.images[2]) { crossfade(true) }
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(a: Post, b: Post) = a.postDate == b.postDate
        override fun areContentsTheSame(a: Post, b: Post) = a == b
    }
}
