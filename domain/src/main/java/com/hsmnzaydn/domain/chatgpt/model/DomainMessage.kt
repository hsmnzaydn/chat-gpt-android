package com.hsmnzaydn.domain.chatgpt.model

data class DomainMessage(
    val messageId: Int,
    val message: String,
    val messageSender: MessageSender,
)
