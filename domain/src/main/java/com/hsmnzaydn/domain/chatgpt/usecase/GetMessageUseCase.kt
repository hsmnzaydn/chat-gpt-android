package com.hsmnzaydn.domain.chatgpt.usecase

import com.hsmnzaydn.domain.chatgpt.UIState
import com.hsmnzaydn.domain.chatgpt.mapper.DomainMessageListToUIMessageListMapper
import com.hsmnzaydn.domain.chatgpt.model.UIMessage
import com.hsmnzaydn.domain.chatgpt.repository.ChatGPTRepository
import com.hsmnzaydn.domain.utility.getUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(
    private val chatGPTRepository: ChatGPTRepository,
    private val domainMessageListToUIMessageListMapper: DomainMessageListToUIMessageListMapper,
) {

    suspend fun getMessage(): Flow<UIState<List<UIMessage>>> = flow {
        chatGPTRepository.getMessages().collect {
            emit(it.getUIState(domainMessageListToUIMessageListMapper))
        }
    }
}