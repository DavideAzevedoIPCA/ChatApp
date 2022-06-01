package com.example.chatapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "messages")
class Message(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "sentBy") val sentBy: String,
    @ColumnInfo(name = "sendAt") val sendAt: Timestamp,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "media_url") val media_url: String,
    @ColumnInfo(name = "state") val state: MessageState,
    @ColumnInfo(name = "id") val conv_uid: String) {
}

enum class MessageState {
    SENDED, RECEIVED, READ
}
