package com.hsmnzaydn.chatgptv2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.hsmnzaydn.chatgptv2.ui.chat.ChatViewModel
import com.hsmnzaydn.chatgptv2.ui.chat.state.ChatScreenState
import com.hsmnzaydn.domain.chatgpt.UIState
import com.hsmnzaydn.domain.chatgpt.model.MessageSender
import com.hsmnzaydn.domain.chatgpt.model.UIMessage
import com.hsmnzaydn.domain.chatgpt.usecase.GetMessageUseCase
import com.hsmnzaydn.domain.chatgpt.usecase.SendMessageUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ChatViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var chatViewModel: ChatViewModel

    @MockK
    private lateinit var getMessageUseCase: GetMessageUseCase

    @MockK
    private lateinit var sendMessageUseCase: SendMessageUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(StandardTestDispatcher())
        chatViewModel = ChatViewModel(
            sendMessageUseCase = sendMessageUseCase,
            getMessageUseCase = getMessageUseCase
        )
    }

    @Test
    fun when_get_data_is_state_success() = runTest {
        coEvery { getMessageUseCase.getMessage() } returns flow {
            emit(UIState.Success(listOf(
                UIMessage(
                    messageId = 0,
                    message = "Selam",
                    messageSender = MessageSender.OPPOSITE
                )
            )))
        }

        chatViewModel.getMessages()

        advanceTimeBy(1000L)
        assertThat(chatViewModel.uiState.value).isInstanceOf(ChatScreenState.LoadMessagesState::class.java)
    }

    @Test
    fun when_last_message_sender_is_me_is_state_waiting() = runTest {
        coEvery { getMessageUseCase.getMessage() } returns flow {
            emit(UIState.Success(listOf(
                UIMessage(
                    messageId = 0,
                    message = "Selam",
                    messageSender = MessageSender.ME
                )
            )))
        }

        chatViewModel.getMessages()

        advanceTimeBy(1000L)
        assertThat(chatViewModel.uiState.value).isInstanceOf(ChatScreenState.WaitingMessageState::class.java)
    }

    @Test
    fun when_answers_overload_limit_character_is_state_show_ad() = runTest {
        coEvery { getMessageUseCase.getMessage() } returns flow {
            emit(UIState.Success(listOf(
                UIMessage(
                    messageId = 0,
                    message = "Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,SelamSelam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,SelamSelam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam,Selam",
                    messageSender = MessageSender.OPPOSITE
                )
            )))
        }

        chatViewModel.getMessages()

        advanceTimeBy(10L)
        assertThat(chatViewModel.uiState.value).isInstanceOf(ChatScreenState.ShowAdState::class.java)
    }
}