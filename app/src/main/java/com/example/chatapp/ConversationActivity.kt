package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.models.Conversation
import com.google.gson.Gson

class ConversationActivity : AppCompatActivity() {

    var conversation : Conversation = Conversation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convesation)


        var a = this.intent.getStringExtra("conversationJSON")

        conversation = Gson().fromJson(this.intent.getStringExtra("conversationJSON"),Conversation::class.java)
    }
}