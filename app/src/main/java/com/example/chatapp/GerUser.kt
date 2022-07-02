package com.example.chatapp

import android.content.Context
import com.example.chatapp.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class GerUser {

    val db = FirebaseFirestore.getInstance()

    fun getUser(user_uid: String, context: Context){
        var user : User = User()

        db.collection("users")
            .document(user_uid)
            .get()
            .addOnSuccessListener { doc ->
                user = doc.toObject<User>()!!
                if (context is HomeActivity){
                    (context as HomeActivity).setUserOnActivity(user)
                }
            }
    }
}