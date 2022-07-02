package com.example.chatapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.chatapp.adapters.UserConvMessageRecAdapter
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message
import com.example.chatapp.models.User
import com.example.chatapp.utils.InternetUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import java.lang.Exception

class ConversationActivity : AppCompatActivity() {

    var conversation : Conversation = Conversation()
    var user : User = User()
    var gerMessage : GerMessage = GerMessage()
    private lateinit var dbSQLite : AppDatabase
    lateinit var recyclerView : RecyclerView
    var dataMessage : List<Message> = emptyList()
    var dataUsers : List<User> = emptyList()
    private var  internetUtils : InternetUtils = InternetUtils()

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
        recyclerView.adapter = UserConvMessageRecAdapter(dataMessage.toMutableList(), dataUsers.toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val action = intent.getStringExtra("action")

            if (action == "GET_MSG"){
                dataMessage = dbSQLite.messageDao().getAllByConversation(conversation.id)
                (recyclerView.adapter as UserConvMessageRecAdapter).refreshData(dataMessage,dataUsers)
            }
            Log.d("receiver", "Got message: $action")
        }
    }

    fun loadData(){

        if (internetUtils.checkForInternet(this)){

            LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, IntentFilter("MSG"))

            gerMessage.getMessages(conversation.id, dbSQLite)
            //TODO add get users
        }
        else{
            dataMessage = dbSQLite.messageDao().getAllByConversation(conversation.id)
            //TODO get users
        }


    }
}