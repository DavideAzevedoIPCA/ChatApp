package com.example.chatapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.type.DateTime
import java.sql.Date
import java.sql.Timestamp

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "sentBy") val sentBy: String,
    @ColumnInfo(name = "sendAt") val sendAt: Date?,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "media_url") val media_url: String,
    @ColumnInfo(name = "state") val state: MessageState,
    @ColumnInfo(name = "conv_uid") val conv_uid: String) {
}

enum class MessageState {
    SENDED, RECEIVED, READ
}
