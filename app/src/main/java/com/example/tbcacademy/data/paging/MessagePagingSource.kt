package com.example.tbcacademy.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbcacademy.common.NetworkHelper
import com.example.tbcacademy.data.remote.MessageApi
import com.example.tbcacademy.domain.model.Message
import com.example.tbcacademy.domain.model.MessageType

class MessagePagingSource(
    private val api: MessageApi,
    private val networkHelper: NetworkHelper,
    private val query: String = ""
) : PagingSource<Int, Message>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {
        return try {
            val page = params.key ?: 1

            val response = api.getMessage(page = page, pageSize = 20)

            val mapped = response.map { dto ->
                val typeString = dto.lastMessageType ?: dto.legacyMessageType ?: "text"
                Message(
                    id = dto.id,
                    image = dto.image,
                    owner = dto.owner,
                    lastMessage = dto.lastMessage,
                    lastActive = dto.lastActive,
                    unreadMessages = dto.unreadMessages,
                    isTyping = dto.isTyping,
                    lastMessageType = when (typeString.lowercase()) {
                        "text" -> MessageType.TEXT
                        "file" -> MessageType.FILE
                        "voice" -> MessageType.VOICE
                        else -> MessageType.TEXT
                    }
                )
            }

            val filtered = if (query.isBlank()) mapped else mapped.filter {
                it.owner.contains(query, ignoreCase = true)
            }

            LoadResult.Page(
                data = filtered,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (filtered.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Message>): Int? = null
}
