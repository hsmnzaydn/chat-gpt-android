package com.hsmnzaydn.domain.chatgpt.mapper

import com.google.common.truth.Truth
import com.hsmnzaydn.domain.chatgpt.model.DomainMessage
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import org.junit.Test


internal class DomainMessageToUIMessageMapperTest{

    private val mapper = DomainMessageToUIMessageMapper()

    @Test
    fun test_domain_message_to_ui_message_mapper(){
        val domainMessage = DomainMessage(
            messageId = 0,
            message = "Selam",
            messageSender = MessageSender.ME,
        )

        val uiMessage = mapper.map(domainMessage)


        Truth.assertThat(uiMessage.messageId).isEqualTo(0)
        Truth.assertThat(uiMessage.message).isEqualTo("Selam")
        Truth.assertThat(uiMessage.messageSender).isEqualTo(MessageSender.ME)

    }
}