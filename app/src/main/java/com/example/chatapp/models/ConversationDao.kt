package com.example.chatapp.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ConversationDao {

    @Query("SELECT * FROM conversations ORDER BY title")
    fun getAll() : List<Conversation>

    @Query("SELECT * FROM conversations WHERE id = :id")
    fun findById(id : String) : Conversation

    @Insert
    fun insertConversation(conversation: Conversation)

    @Update
    fun updateConversation(conversation: Conversation)
}