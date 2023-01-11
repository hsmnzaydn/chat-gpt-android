package com.hsmnzaydn.domain.chatgpt.mapper

import com.hsmnzaydn.domain.chatgpt.model.DomainMessage
import com.hsmnzaydn.domain.chatgpt.model.UIMessage
import com.hsmnzaydn.domain.mapper.Mapper
import javax.inject.Inject

class DomainMessageToUIMessageMapper @Inject constructor() : Mapper<DomainMessage, UIMessage> {
    override fun map(response: DomainMessage): UIMessage {
        return UIMessage(
            messageId = response.messageId,
            message = response.message,
            messageSender = response.messageSender
        )
    }
}