package com.example.tbcacademy.presentation.screens.contacts

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.tbcacademy.domain.model.Message
import com.example.tbcacademy.domain.model.MessageType
import com.example.tbcacademy.databinding.ContactsItemBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcacademy.R

class ContactsPagingAdapter :
    PagingDataAdapter<Message, ContactsPagingAdapter.ContactsViewHolder>(DiffCallback()) {

    inner class ContactsViewHolder(private val binding: ContactsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message?) {
            binding.apply {
                if (message == null) return

                tvContactName.text = message.owner
                tvTimeSent.text = message.lastActive
                tvLastMessage.text = message.lastMessage

                ivMessageType.visibility = when (message.lastMessageType) {
                    MessageType.TEXT -> View.GONE
                    MessageType.FILE -> {
                        ivMessageType.setImageResource(R.drawable.ic_attachment)
                        View.VISIBLE
                    }
                    MessageType.VOICE -> {
                        ivMessageType.setImageResource(R.drawable.ic_voice)
                        View.VISIBLE
                    }
                }

                tvUnreadCount.text = message.unreadMessages.toString()
                tvUnreadCount.visibility = View.VISIBLE

                Glide.with(root.context)
                    .load(message.image)
                    .circleCrop()
                    .placeholder(R.drawable.ic_default)
                    .into(ivContactBackground)
            }
        }

    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactsViewHolder(
            ContactsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    class DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
    }
}
