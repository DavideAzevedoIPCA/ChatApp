package com.example.chatapp.models

import androidx.room.*

@Entity(tableName = "conversations")
data class Conversation(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "users") var users: List<String?>?) {
}

