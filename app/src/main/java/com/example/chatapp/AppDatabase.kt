package com.example.chatapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chatapp.models.*

@Database(entities = [Conversation::class, Message::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "conversations.db")
            .build()
    }
}