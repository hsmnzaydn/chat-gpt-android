package com.hsmnzaydn.domain.chatgpt.mapper

import com.hsmnzaydn.domain.chatgpt.model.DomainMessage
import com.hsmnzaydn.domain.chatgpt.model.UIMessage
import com.hsmnzaydn.domain.mapper.Mapper
import javax.inject.Inject

class DomainMessageListToUIMessageListMapper @Inject constructor() : Mapper<List<DomainMessage>, List<UIMessage>> {
    override fun map(response: List<DomainMessage>): List<UIMessage> {
        return response.map {
            UIMessage(
                messageId = it.messageId,
                message = it.message,
                messageSender = it.messageSender
            )
        }
    }
}