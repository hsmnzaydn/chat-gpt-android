package com.hsmnzaydn.chatgptv2.ui.chat

import androidx.lifecycle.viewModelScope
import com.hsmnzaydn.chatgptv2.base.BaseViewModel
import com.hsmnzaydn.chatgptv2.ui.chat.action.ChatFragmentAction
import com.hsmnzaydn.chatgptv2.ui.chat.state.ChatScreenState
import com.hsmnzaydn.domain.chatgpt.UIState
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import com.hsmnzaydn.domain.chatgpt.model.UIMessage
import com.hsmnzaydn.domain.chatgpt.usecase.GetMessageUseCase
import com.hsmnzaydn.domain.chatgpt.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase: GetMessageUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<ChatScreenState>(ChatScreenState.LoadingState)
    val uiState = _uiState.asStateFlow()

    init {
        getMessages()
    }

    fun action(action: ChatFragmentAction) {
        when (action) {
            is ChatFragmentAction.SendMessageAction -> sendMessage(action.message)
        }
    }

    fun getMessages() {
        viewModelScope.launch {
            getMessageUseCase.getMessage().collect(this@ChatViewModel::stateManager)
        }
    }

    private fun stateManager(uiState: UIState<List<UIMessage>>) {
        when (uiState) {
            is UIState.Success -> loadMessage(uiState.body)
            is UIState.ErrorState -> showError(uiState.message)
        }
    }

    private fun loadMessage(list: List<UIMessage>) {
        val state = when (list.last().messageSender) {
            MessageSender.ME -> ChatScreenState.WaitingMessageState(list)
            MessageSender.OPPOSITE -> {
                hideLoading()
                if (list.last().message.length > SHOW_AD_CHARACTER_LIMIT) {
                    ChatScreenState.ShowAdState(list)
                } else {
                    ChatScreenState.LoadMessagesState(list)
                }
            }
            else -> {
                ChatScreenState.LoadMessagesState(list)
            }
        }
        _uiState.value = state
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            showLoading()
            sendMessageUseCase.sendMessage(message).collect {

            }
        }
    }

    companion object {
        private const val SHOW_AD_CHARACTER_LIMIT = 100
    }
}