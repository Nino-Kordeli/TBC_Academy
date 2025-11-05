package com.example.tbcacademy.screen.chat

import ChatViewModel
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcacademy.R
import com.example.tbcacademy.screen.chat.adapter.ChatAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val viewModel: ChatViewModel by viewModels()
    private lateinit var adapter: ChatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvChat = view.findViewById<RecyclerView>(R.id.rvChatMessages)
        val etMessage = view.findViewById<AppCompatEditText>(R.id.etChatField)
        val btnSend = view.findViewById<AppCompatImageView>(R.id.btnSendButton)

        adapter = ChatAdapter()
        rvChat.layoutManager = LinearLayoutManager(requireContext())
        rvChat.adapter = adapter

        btnSend.setOnClickListener {
            val text = etMessage.text.toString()
            if (text.isNotBlank()) {
                viewModel.sendMessage(text)
                etMessage.text?.clear()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.messages.collectLatest { messages ->
                adapter.submitList(messages) {
                    rvChat.scrollToPosition(messages.size - 1)
                }
            }
        }
    }
}
