package com.example.chatapp.models

import java.sql.Timestamp

class Message(
    val id: String,
    val time: Timestamp,
    val text: String,
    val media_url: String,
    val state: State,
    val sender_uid: String,
    val conv_uid: String) {
}

enum class State {
    SENDED, RECEIVED, READ
}
