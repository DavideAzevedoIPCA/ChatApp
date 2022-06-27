package com.example.chatapp.models


import androidx.room.*
import java.sql.Date
import java.util.*

@Entity(tableName = "conversations")
data class Conversation(
    @PrimaryKey @ColumnInfo(name = "id") var id : String,
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "users") var users : List<String?>?,
    @ColumnInfo(name = "lastMessage") var lastMessage : Message?
    ){
    constructor() : this("","", emptyList(),Message())
    }