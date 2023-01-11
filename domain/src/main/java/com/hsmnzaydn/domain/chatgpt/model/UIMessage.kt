package com.hsmnzaydn.domain.chatgpt.model

data class UIMessage(
    val messageId: Int,
    val message: String,
    val messageSender: MessageSender,
)
