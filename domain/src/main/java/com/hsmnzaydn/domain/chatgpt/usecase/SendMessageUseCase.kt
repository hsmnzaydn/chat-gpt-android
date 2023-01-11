package com.hsmnzaydn.domain.chatgpt.usecase


import com.hsmnzaydn.domain.chatgpt.UIState
import com.hsmnzaydn.domain.chatgpt.mapper.DomainMessageToUIMessageMapper
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import com.hsmnzaydn.domain.chatgpt.model.UIMessage
import com.hsmnzaydn.domain.chatgpt.repository.ChatGPTRepository
import com.hsmnzaydn.domain.utility.getUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatGPTRepository: ChatGPTRepository,
    private val domainMessageToUIMessageMapper: DomainMessageToUIMessageMapper,
) {

    suspend fun sendMessage(text: String): Flow<UIState<UIMessage>> = flow {
        chatGPTRepository.askQuestion(
            message = text,
            messageSender = MessageSender.ME,
        ).collect {
            emit(it.getUIState(domainMessageToUIMessageMapper))
        }
    }
}