package com.example.chatapp

import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message
import com.example.chatapp.models.MessageDao
import com.example.chatapp.models.MessageState
import com.example.chatapp.utils.MediaUtils
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.sql.Date

class GerMessage {
    val db = FirebaseFirestore.getInstance()
    val storage = Firebase.storage("gs://chatapp-d77ab.appspot.com")
    val mediaUtils : MediaUtils = MediaUtils()

    fun setMessage(message: Message,conversation: Conversation): String{

        val docRef = db.collection("messages").document()
        message.id =  docRef.id
        message.state = MessageState.SENDED
        docRef.set(message)
            .addOnSuccessListener {
                if(conversation.id.length > 0){
                    db.collection("conversations").document(conversation.id)
                        .update("lastMessage",message)
                        .addOnSuccessListener {
                            Log.d("UPDCONV", "DocumentSnapshot successfully updated!")
                        }
                        .addOnFailureListener{
                            Log.d("UPDCONV", "Error updating document $conversation.id")
                        }
                }
            }

        return  message.id
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

                    message.mapMessage(it.data)

                    if (message.id.isNotEmpty()){
                        if (dbSQLite.messageDao().findById(message.id) == null){
                            dbSQLite.messageDao().insertMessage(message)
                        } else{
                            dbSQLite.messageDao().updateMessage(message)
                        }

                        if (message.media_url.isNotEmpty()&&message.media_url != "null"){
                            mediaUtils.dowloadMedia(message.media_url)
                        }

                    }
                }
                sendMessage()
            }
    }

    private fun sendMessage() {
        Log.d("sender", "Broadcasting message")
        val intent = Intent("MSGS")
        // You can also include some extra data.
        intent.putExtra("action", "GET_MSG")
        val context = MyApplication.applicationContext()
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

}