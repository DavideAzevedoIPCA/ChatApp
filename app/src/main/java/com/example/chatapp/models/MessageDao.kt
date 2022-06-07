package com.example.chatapp.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY sendAt ")
    fun getAll() : List<Message>

    @Query("SELECT * FROM messages WHERE conv_uid = :id ORDER BY sendAt ASC")
    fun getAllByConversation(id : String) : List<Message>

    @Query("SELECT * FROM messages WHERE id = :id")
    fun findById(id : String) : Message

    @Insert
    fun insertMessage(message: Message)

    @Update
    fun updateMessage(message: Message)
}