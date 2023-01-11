package com.hsmnzaydn.chatgptv2.ui.chat.state

import com.hsmnzaydn.domain.chatgpt.model.UIMessage

sealed class ChatScreenState {
    data class LoadMessagesState(val messages: List<UIMessage>) : ChatScreenState()
    data class WaitingMessageState(val messages: List<UIMessage>) : ChatScreenState()
    data class ShowAdState(val messages: List<UIMessage>) : ChatScreenState()
    object LoadingState : ChatScreenState()
}