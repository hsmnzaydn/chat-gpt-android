package com.hsmnzaydn.data.services.chatgpt

import com.hsmnzaydn.data.mapper.AskResponseMapper
import com.hsmnzaydn.data.mapper.MessageEntityMapper
import com.hsmnzaydn.remotedatasource.services.chatgpt.request.AskQuestionRequest
import com.hsmnzaydn.data.utility.toDomainState
import com.hsmnzaydn.domain.chatgpt.DomainState
import com.hsmnzaydn.domain.chatgpt.model.DomainMessage
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import com.hsmnzaydn.domain.chatgpt.repository.ChatGPTRepository
import com.hsmnzaydn.localdatasource.chatgpt.dao.MessageDAO
import com.hsmnzaydn.localdatasource.chatgpt.entity.MessageEntity
import com.hsmnzaydn.remotedatasource.model.NetworkResponse
import com.hsmnzaydn.remotedatasource.services.chatgpt.ChatGPTService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit
import javax.inject.Inject

class ChatGTPRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit,
    private val askResponseMapper: AskResponseMapper,
    private val messageEntityMapper: MessageEntityMapper,
    private val messageDAO: MessageDAO,
) : ChatGPTRepository {

    private val services by lazy {
        retrofit.create(ChatGPTService::class.java)
    }

    override suspend fun askQuestion(message: String, messageSender: MessageSender): Flow<DomainState<DomainMessage>> =
        flow {
            coroutineScope {
                messageDAO.insert(MessageEntity(
                    sender = messageSender.name,
                    message = message
                ))
                val response = async {
                    services.askQuestion(AskQuestionRequest(
                        maxTokens = 1000,
                        model = "text-davinci-003",
                        prompt = message,
                        temperature = 0
                    ))
                }
                messageDAO.insert(MessageEntity(
                    sender = MessageSender.OPPOSITE.name,
                    message = (response.await() as? NetworkResponse.Success)?.body?.choices?.get(0)?.text?:"There is a problem please try again"
                ))
                emit(response.await().toDomainState(askResponseMapper))
            }
        }.flowOn(Dispatchers.IO)


    override suspend fun getMessages(): Flow<DomainState<List<DomainMessage>>> = flow {
        messageDAO.getRealTimeMessages()
            .catch {
                DomainState.ErrorState<List<DomainMessage>>("There is a problem")
            }.collect {
                if (it.isNotEmpty()){
                    emit(
                        DomainState.Success(it.map {
                            messageEntityMapper.map(it)
                        })
                    )
                }
            }
    }
}