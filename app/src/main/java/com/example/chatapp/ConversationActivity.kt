package com.example.chatapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.chatapp.adapters.UserConvMessageRecAdapter
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message
import com.example.chatapp.models.MessageState
import com.example.chatapp.models.User
import com.example.chatapp.utils.InternetUtils
import com.google.gson.Gson
import java.lang.Exception
import java.util.*

class ConversationActivity : AppCompatActivity() {

    var conversation : Conversation = Conversation()
    var user : User = User()
    var gerMessage : GerMessage = GerMessage()
    var gerUser : GerUser = GerUser()
    private lateinit var dbSQLite : AppDatabase
    lateinit var recyclerView : RecyclerView
    var dataMessage : MutableList<Message> = emptyList<Message>().toMutableList()
    var dataUsers : MutableList<User> = emptyList<User>().toMutableList()
    private var  internetUtils : InternetUtils = InternetUtils()
    private lateinit var editTextMessage : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convesation)

        conversation = Gson().fromJson(this.intent
            .getStringExtra("conversationJSON"), Conversation::class.java)

        try {
            user = Gson().fromJson(this.intent
                .getStringExtra("userJSON"), User::class.java)
        }
        catch(e: Exception) {

        }


        dbSQLite = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "conversations.db")
            .allowMainThreadQueries() //martelado, need to be fixed
            .build()

        loadData()

        recyclerView = findViewById(R.id.actConversation_message_reclist)
        recyclerView.adapter = UserConvMessageRecAdapter(dataMessage.toMutableList(), dataUsers.toMutableList(),user)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.smoothScrollToPosition(recyclerView.bottom)

        editTextMessage = findViewById(R.id.actConversation_message_et)
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val action = intent.getStringExtra("action")

            if (action == "GET_MSG"){
                dataMessage = dbSQLite.messageDao().getAllByConversation(conversation.id).toMutableList()
            }
            if (action == "GET_USERS_CONV"){
                dataUsers = dbSQLite.userDao().getByIN(conversation.users as List<String>).toMutableList()
            }

            if (dataMessage.isNotEmpty()&&dataUsers.isNotEmpty()){
                (recyclerView.adapter as UserConvMessageRecAdapter).refreshData(dataMessage,dataUsers)
                recyclerView.smoothScrollToPosition(recyclerView.bottom)
            }

            Log.d("receiver", "Got message: $action")
        }
    }

    private fun loadData(){

        if (internetUtils.checkForInternet(this)){

            LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, IntentFilter("MSGS"))

            gerMessage.getMessages(conversation.id, dbSQLite)
            gerUser.getUsersFromConversation(conversation.users, dbSQLite)
        }
        else{
            dataMessage = dbSQLite.messageDao().getAllByConversation(conversation.id).toMutableList()
            dataUsers = dbSQLite.userDao().getByIN(conversation.users as List<String>).toMutableList()
        }
    }

    fun addMessage(view: View) {

        var message = Message("",user.uid, Date(),editTextMessage.text.toString(),"",MessageState.NONE, conversation.id)
        editTextMessage.text.clear()
        message.id = gerMessage.setMessage(message)

        dbSQLite.messageDao().insertMessage(message)
        dataMessage.add(message)
        (recyclerView.adapter as UserConvMessageRecAdapter).refreshData(dataMessage,dataUsers)
        recyclerView.smoothScrollToPosition(recyclerView.bottom)


    }
}