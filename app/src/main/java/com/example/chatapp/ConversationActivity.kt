package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.models.Conversation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class ConversationActivity : AppCompatActivity() {

    var conversation : Conversation = Conversation()
    var gerMessage : GerMessage = GerMessage()
    private lateinit var dbSQLite : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convesation)

        conversation = Gson().fromJson(this.intent
            .getStringExtra("conversationJSON"),Conversation::class.java)

    }
}