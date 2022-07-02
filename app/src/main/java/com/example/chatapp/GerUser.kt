package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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

    fun getUsersFromConversation(users: List<String?>?, dbSQLite : AppDatabase) {
        if (users != null) {
            db.collection("users")
                .whereIn("uid",users.toMutableList())
                .addSnapshotListener { values, e ->

                    if (e != null){
                        Log.w("GETDATAUSERS", "Listen failed.", e)
                    }

                    var user : User
                    values?.forEach{
                        user = User()

                        user.uid = it.data["uid"].toString()
                        user.name = it.data["name"].toString()
                        user.email = it.data["email"].toString()
                        user.photo_url = it.data["photo_url"].toString()

                        if (user.uid.isNotEmpty()){
                            if (dbSQLite.userDao().findById(user.uid) == null){
                                dbSQLite.userDao().insertUser(user)
                            } else {
                                dbSQLite.userDao().updateUser(user)
                            }
                        }
                    }
                    sendMessage("MSGS","GET_USERS_CONV")
                }
        }
    }

    private fun sendMessage(action : String, value : String) {
        Log.d("sender", "Broadcasting message")
        val intent = Intent(action)
        // You can also include some extra data.
        intent.putExtra("action", value)
        val context = MyApplication.applicationContext()
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}