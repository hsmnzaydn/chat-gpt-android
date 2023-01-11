package com.hsmnzaydn.localdatasource.chatgpt

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.hsmnzaydn.localdatasource.chatgpt.dao.MessageDAO
import com.hsmnzaydn.localdatasource.chatgpt.db.MessageDatabase
import com.hsmnzaydn.localdatasource.chatgpt.entity.MessageEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch

@SmallTest
@ExperimentalCoroutinesApi
class MessageDAOTest {
    private lateinit var db : MessageDatabase
    private lateinit var messageDAO: MessageDAO

    @Before
    fun setup(){
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MessageDatabase::class.java
        ).allowMainThreadQueries().build()
        Dispatchers.setMain(UnconfinedTestDispatcher())

        messageDAO = db.getMessageDAO()
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun test_insert_message() = runTest {
        val messageEntity = MessageEntity(
            messageId = 1,
            sender = "ME",
            message = "hi",
            time = 0L,
        )

        messageDAO.insert(messageEntity)

        messageDAO.getAllMessages().test{
            val list = awaitItem()
            Truth.assertThat(list).contains(messageEntity)
            cancel()
        }

    }
}