import androidx.lifecycle.ViewModel
import com.example.tbcacademy.screen.chat.model.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val newMessage = ChatMessage(
            text = text,
            isLeft = (_messages.value.size % 2 == 0)
        )

        _messages.update { current ->
            current + newMessage
        }
    }
}
