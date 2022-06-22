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
            .addSnapshotListener { values, e ->
                if (e != null){
                    Log.w("GETDATA", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (values != null && values.documents.isNotEmpty()){
                    Log.d("GETDATA", values.toString())
                }
                else {
                    Log.d("GETDATA", "data: null")
                }

                var conversation : Conversation = Conversation("","", emptyList<String?>())
                values?.forEach {

                    conversation.id = it.id
                    conversation.title = it.data["title"].toString()
                    conversation.users = it.data["users"] as List<String?>?

                    if (!conversation.id.isNullOrEmpty())
                    {
                        var c1 = dbSQLite.conversationDao()
                            .findById(conversation.id)

                        if (dbSQLite.conversationDao()
                                .findById(conversation.id) == null) {
                            dbSQLite.conversationDao().insertConversation(conversation)
                        } else {
                            dbSQLite.conversationDao().updateConversation(conversation)
                        }
                    }
                }
                sendMessage()
            }
    }

    private fun sendMessage() {
        Log.d("sender", "Broadcasting message")
        val intent = Intent("custom-event-name")
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!")
        val context = MyApplication.applicationContext()
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}