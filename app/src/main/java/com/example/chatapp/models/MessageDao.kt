package com.example.chatapp.models

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY sendAt")
    fun getAll() : List<Message>

    @Query("SELECT * FROM messages ORDER BY sendAt")
    fun getAllByConversation() : List<Message>

    @Query("SELECT * FROM messages WHERE id = :id")
    fun findById(id : String) : Message

}