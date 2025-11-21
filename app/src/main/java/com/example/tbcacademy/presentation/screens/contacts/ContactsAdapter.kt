import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.ContactsItemBinding
import com.example.tbcacademy.domain.model.Message
import com.example.tbcacademy.domain.model.MessageType

class ContactsAdapter : ListAdapter<Message, ContactsAdapter.ContactsViewHolder>(DiffCallback()) {

    inner class ContactsViewHolder(private val binding: ContactsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) = binding.apply {
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
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivContactBackground)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactsViewHolder(
            ContactsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
    }
}
