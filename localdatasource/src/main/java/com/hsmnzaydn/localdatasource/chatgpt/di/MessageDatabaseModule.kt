package com.hsmnzaydn.localdatasource.chatgpt.di

import android.content.Context
import androidx.room.Room
import com.hsmnzaydn.localdatasource.chatgpt.db.MessageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MessageDatabaseModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MessageDatabase::class.java,
        "message_db"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: MessageDatabase) = db.getMessageDAO()

}