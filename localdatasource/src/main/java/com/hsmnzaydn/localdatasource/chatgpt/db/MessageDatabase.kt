package com.hsmnzaydn.localdatasource.chatgpt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hsmnzaydn.localdatasource.chatgpt.dao.MessageDAO
import com.hsmnzaydn.localdatasource.chatgpt.entity.MessageEntity

@Database(
    entities = [MessageEntity::class], // Tell the database the entries will hold data of this type
    version = 1
)

abstract class MessageDatabase : RoomDatabase() {

    abstract fun getMessageDAO(): MessageDAO
}