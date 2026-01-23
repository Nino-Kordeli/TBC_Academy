package com.example.tbcacademy.presentation.chat.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tbcacademy.presentation.theme.DarkerBlue
import com.example.tbcacademy.presentation.theme.LightGray
import com.example.tbcacademy.presentation.theme.LightGreen
import com.example.tbcacademy.presentation.theme.MediumGray
import com.example.tbcacademy.presentation.theme.White
import com.example.tbcacademy.R
import com.example.tbcacademy.domain.model.MessageType
import com.example.tbcacademy.presentation.chat.contract.ChatEvent
import com.example.tbcacademy.presentation.chat.vm.ChatViewModel
import com.example.tbcacademy.presentation.common.BaseScreen
import com.example.tbcacademy.presentation.model.ChatUiModel
import com.example.tbcacademy.presentation.theme.DarkBlue

@Composable
fun ChatScreen(
    navigator: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        onSideEffect = {},
    ) { state, onEvent ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = state.searchQuery,
                    onValueChange = { onEvent(ChatEvent.OnSearchQueryChange(it)) },
                    placeholder = { Text("Search") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = DarkerBlue,
                        focusedContainerColor = DarkerBlue,
                        focusedTextColor = White,
                        unfocusedTextColor = LightGray
                    ), shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.width(19.dp))

                Button(
                    onClick = { onEvent(ChatEvent.OnSearchClick) },
                    modifier = Modifier.size(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGreen,
                        contentColor = Color.White,
                    ), shape = RoundedCornerShape(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_options),
                        contentDescription = null
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
            ) {
                items(state.chats) { chat ->
                    ChatItem(chat)

                    HorizontalDivider(
                        color = MediumGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: ChatUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.Magenta, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("😎", fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.owner,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = chat.lastActive,
                    color = LightGray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (chat.isTyping) {
                    Text(
                        "typing...",
                        color = LightGreen,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                } else {
                    when (chat.lastMessageType) {
                        MessageType.TEXT -> Text(
                            chat.lastMessage,
                            color = LightGray,
                            maxLines = 2,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )

                        MessageType.VOICE -> Text("Voice message", color = LightGray)
                        MessageType.FILE -> Text("File", color = LightGray)
                    }
                }

                if (chat.unreadMessages > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(LightGreen, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            chat.unreadMessages.toString(),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}