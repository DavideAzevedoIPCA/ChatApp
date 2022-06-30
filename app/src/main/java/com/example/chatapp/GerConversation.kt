package com.example.chatapp

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message
import com.example.chatapp.models.MessageState
import com.example.chatapp.models.User
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class GerConversation {

    val db = FirebaseFirestore.getInstance()

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

        db.collection("conversations")
            .whereArrayContains("users", user_uid)
            .addSnapshotListener { values, e ->
                if (e != null){
                    Log.w("GETDATA", "Listen failed.", e)
                    return@addSnapshotListener
                }

                //TODO: validar este IF, se é necessário ou não
                if (values != null && values.documents.isNotEmpty()){
                    Log.d("GETDATA", values.toString())
                }
                else {
                    Log.d("GETDATA", "data: null")
                }

                var conversation: Conversation

                values?.forEach {

                    conversation = Conversation()

                    conversation.id = it.id
                    conversation.title = it.data["title"].toString()
                    conversation.users = it.data["users"] as List<String?>?

                    var objMap = it.get("lastMessage") as Map<String, Any>

                    if (!objMap.isNullOrEmpty()){
                        conversation.lastMessage?.mapMessage(objMap)
                    }

                    if (!conversation.id.isNullOrEmpty())
                    {
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
        val intent = Intent("CONVS")
        // You can also include some extra data.
        intent.putExtra("action", "GET_CONVS")
        val context = MyApplication.applicationContext()
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}