package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.User
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    var user: User = User("","","")
    private lateinit var auth: FirebaseAuth
    val gerConversation : GerConversation = GerConversation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        user.uid = auth.currentUser!!.uid.toString()
        user.name = auth.currentUser!!.email.toString()

        Log.d("HOME", "user_uid:"+user.uid.toString())
        Log.d("HOME", "user_name:"+user.name.toString())

        var conv : Conversation = Conversation("","conversa teste", listOf(user.uid,"qweqweq"))
        gerConversation.setConversation(conv)
    }

    fun launchFragment(view: View) {}
    fun launchFragmentUser(view: View) {}


    fun getConversations(){

    }

    fun addConversation(){

    }
}