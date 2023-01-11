package com.hsmnzaydn.localdatasource.chatgpt.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hsmnzaydn.localdatasource.chatgpt.entity.MessageEntity
import kotlinx.coroutines.flow.*

@Dao
interface MessageDAO {
    @Insert
    fun insert(message: MessageEntity): Long

    @Query("SELECT*FROM messages")
    fun getAllMessages(): Flow<List<MessageEntity>>

    suspend fun getRealTimeMessages(): Flow<List<MessageEntity>> =
        getAllMessages().distinctUntilChanged()
}