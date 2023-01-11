package com.hsmnzaydn.localdatasource.chatgpt.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "messageId")
    var messageId: Int = 0,

    @ColumnInfo(name = "sender")
    var sender: String,

    @ColumnInfo(name = "message")
    var message: String,

    @ColumnInfo(name = "sendTime")
    var time: Long = System.currentTimeMillis(),

    )