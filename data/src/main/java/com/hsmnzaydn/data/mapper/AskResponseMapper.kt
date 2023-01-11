package com.hsmnzaydn.data.mapper

import com.hsmnzaydn.domain.chatgpt.model.DomainMessage
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import com.hsmnzaydn.domain.mapper.Mapper
import com.hsmnzaydn.remotedatasource.services.chatgpt.response.AskQuestionResponse
import javax.inject.Inject

class AskResponseMapper @Inject constructor() : Mapper<AskQuestionResponse, DomainMessage> {
    override fun map(response: AskQuestionResponse): DomainMessage {
        return DomainMessage(
            0,
            response.choices[0].text,
            MessageSender.OPPOSITE
        )
    }
}