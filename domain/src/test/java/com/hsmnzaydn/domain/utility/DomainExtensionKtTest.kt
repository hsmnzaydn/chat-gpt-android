package com.hsmnzaydn.domain.utility

import com.google.common.truth.Truth
import com.hsmnzaydn.domain.chatgpt.DomainState
import com.hsmnzaydn.domain.chatgpt.UIState
import com.hsmnzaydn.domain.chatgpt.mapper.DomainMessageToUIMessageMapper
import com.hsmnzaydn.domain.chatgpt.model.DomainMessage
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import org.junit.Test


internal class DomainExtensionKtTest{

    private val domainMessageToUIMessageMapper = DomainMessageToUIMessageMapper()

    @Test
    fun test_success_domain_state_to_success_ui_state_mapper(){
        val domainMessage = DomainMessage(
            messageId = 0,
            message = "Selam",
            messageSender = MessageSender.ME,
        )

        val domainState = DomainState.Success(domainMessage)

        val successState = domainState.getUIState(domainMessageToUIMessageMapper)

        Truth.assertThat(successState).isInstanceOf(UIState.Success::class.java)
    }

    @Test
    fun test_error_domain_state_to_error_ui_state_mapper(){

        val domainState = DomainState.ErrorState<DomainMessage>("")

        val errorState = domainState.getUIState(domainMessageToUIMessageMapper)

        Truth.assertThat(errorState).isInstanceOf(UIState.ErrorState::class.java)
    }
}