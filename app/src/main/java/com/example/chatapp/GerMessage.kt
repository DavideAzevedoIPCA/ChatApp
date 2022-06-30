package com.example.chatapp

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.chatapp.models.Message
import com.example.chatapp.models.MessageDao
import com.example.chatapp.models.MessageState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.sql.Date

class GerMessage {
    val db = FirebaseFirestore.getInstance()

    fun setMessage(message: Message): Boolean{

        return true
    }

    fun getMessages(idConv : String, dbSQLite : AppDatabase) {

        db.collection("messages")
            .whereEqualTo("conv_uid", idConv)
            .addSnapshotListener { values, e ->
                if (e != null){
                    Log.w("GETDATA", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (values != null && values.documents.isNotEmpty()){
                    //Talvez não seja necessário
                }

                var message : Message

                values?.forEach {
                    message = Message()

                    message.id = it.id
                    message.sentBy = it.data["sentBy"].toString()
                    message.sendAt = it.data["sendAt"] as Date?
                    message.text = it.data["text"].toString()
                    message.media_url = it.data["media_url"].toString()
                    message.state = MessageState.fromInt((it.data["state"]as Long).toInt())
                    message.conv_uid = it.data["conv_uid"].toString()

                }
                sendMessage()
            }
    }

    private fun sendMessage() {
        Log.d("sender", "Broadcasting message")
        val intent = Intent("MSG")
        // You can also include some extra data.
        intent.putExtra("action", "GET_MSG")
        val context = MyApplication.applicationContext()
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

}