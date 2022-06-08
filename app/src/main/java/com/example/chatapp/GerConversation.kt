package com.example.chatapp

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.chatapp.models.Conversation
import com.google.firebase.firestore.FirebaseFirestore


class GerConversation {

    val db = FirebaseFirestore.getInstance()

    //var conversation : Conversation = Conversation("","", emptyArray())

    fun setConversation(conversation: Conversation) : Boolean{

        db.collection("conversations")
            .add(conversation)
            .addOnSuccessListener {
                conversation.id = it.id
                Log.d("SAVE", "Successfully:"+conversation.id)
            }
            .addOnFailureListener {
                Log.d("SAVE", "Unsuccessfully")
            }

        return conversation.id.isNotEmpty()
    }

    fun getConversations(user_uid: String, dbSQLite : AppDatabase) {

        var list : MutableList<Conversation> = mutableListOf<Conversation>()

        db.collection("conversations")
            .whereArrayContains("users",user_uid)
            .addSnapshotListener { values, error ->

                values?.forEach {
                    dbSQLite.conversationDao().insertConversation(it.data.entries as Conversation)
                }
                sendMessage()
            }
    }


    private fun sendMessage() {
        Log.d("sender", "Broadcasting message")
        val intent = Intent("custom-event-name")
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!")
        val context = MyApplication.appContext
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}