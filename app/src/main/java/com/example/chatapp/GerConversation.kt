package com.example.chatapp

import android.util.Log
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

    fun getConversations(user_uid: String) : List<Conversation>{
        var list : List<Conversation> = emptyList()

        /*db.collection("conversations")
            .whereArrayContains("users",user_uid)
            .addSnapshotListener()*/
        return list
    }
}