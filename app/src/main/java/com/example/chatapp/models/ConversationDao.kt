package com.example.chatapp.models

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ConversationDao {

    @Query("SELECT * FROM conversations ORDER BY title")
    fun getAll() : List<Conversation>

    @Query("SELECT * FROM conversations WHERE id = :id")
    fun findById(id : String) : Conversation

}