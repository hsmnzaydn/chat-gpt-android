package com.hsmnzaydn.data.di

import com.hsmnzaydn.data.mapper.AskResponseMapper
import com.hsmnzaydn.data.mapper.MessageEntityMapper
import com.hsmnzaydn.data.services.chatgpt.ChatGTPRepositoryImpl
import com.hsmnzaydn.domain.chatgpt.repository.ChatGPTRepository
import com.hsmnzaydn.localdatasource.chatgpt.dao.MessageDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ChatGPTModule {

    @Provides
    fun provideChatGPTRepository(
        retrofit: Retrofit,
        askResponseMapper: AskResponseMapper,
        messageEntityMapper: MessageEntityMapper,
        messageDAO: MessageDAO,
    ): ChatGPTRepository = ChatGTPRepositoryImpl(retrofit, askResponseMapper, messageEntityMapper,messageDAO)
}