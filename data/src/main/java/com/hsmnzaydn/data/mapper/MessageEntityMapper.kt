package com.hsmnzaydn.data.mapper

import com.hsmnzaydn.domain.chatgpt.model.DomainMessage
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import com.hsmnzaydn.domain.mapper.Mapper
import com.hsmnzaydn.localdatasource.chatgpt.entity.MessageEntity
import javax.inject.Inject

class MessageEntityMapper @Inject constructor() : Mapper<MessageEntity, DomainMessage> {

    override fun map(response: MessageEntity): DomainMessage {
        return DomainMessage(
            response.messageId,
            response.message,
            MessageSender.valueOf(response.sender)
        )
    }
}