package com.example.chatapp

import com.example.chatapp.models.Message
import com.example.chatapp.models.MessageDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class GerMessage {
    val db = FirebaseFirestore.getInstance()

    fun setMessage(message: Message): Boolean{

        return true
    }

    fun getMessages(user_uid : String) : List<Message>{
        var list : List<Message> = emptyList()

        return list
    }

}