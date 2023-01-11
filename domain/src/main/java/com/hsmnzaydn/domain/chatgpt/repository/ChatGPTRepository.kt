package com.hsmnzaydn.domain.chatgpt.repository

import com.hsmnzaydn.domain.chatgpt.DomainState
import com.hsmnzaydn.domain.chatgpt.model.DomainMessage
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ChatGPTRepository {
    suspend fun askQuestion(message: String, messageSender: MessageSender): Flow<DomainState<DomainMessage>>
    suspend fun getMessages(): Flow<DomainState<List<DomainMessage>>>
}