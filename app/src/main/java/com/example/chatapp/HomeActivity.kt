package com.example.chatapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.chatapp.adapters.UserConvRecAdapter
import com.example.chatapp.fragments.ConversationInfoFragment
import com.example.chatapp.fragments.ConversationsFragment
import com.example.chatapp.fragments.UserFragment
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.User
import com.example.chatapp.utils.InternetUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {

    var user: User = User("","","","")
    private lateinit var auth: FirebaseAuth
    val gerConversation : GerConversation = GerConversation()
    val gerMessage : GerMessage = GerMessage()
    private lateinit var dbSQLite : AppDatabase
    lateinit var dataConversation : List<Conversation>
    private var  internetUtils : InternetUtils = InternetUtils()
    lateinit var recyclerView : RecyclerView
    lateinit var recyclerViewAdapter: UserConvRecAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        user.uid = auth.currentUser!!.uid.toString()
        user.name = auth.currentUser!!.email.toString()
        Log.d("HOME", "user_uid:"+user.uid.toString())
        Log.d("HOME", "user_name:"+user.name.toString())

        dbSQLite = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "conversations.db")
            .allowMainThreadQueries() //martelado, need to be fixed
            .build()

        if (internetUtils.checkForInternet(this)){
            LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, IntentFilter("custom-event-name"))

            loadData() //faz loading dos dados do firestore para SQLite
        }

        GlobalScope.launch {
            val dataConversation = dbSQLite.conversationDao().getAll()
            val dataMessage = dbSQLite.messageDao().getAll()
        }


        //var conv : Conversation = Conversation("","conversa teste", listOf(user.uid,"qweqweq"))
        //gerConversation.setConversation(conv)
        launchFragment(this.findViewById(android.R.id.content))
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val action = intent.getStringExtra("action")

            if (action == "GET_CONVS"){
                dataConversation = dbSQLite.conversationDao().getAll()
                recyclerViewAdapter.refreshData(dataConversation)
            }
            Log.d("receiver", "Got message: $action")
        }
    }

    fun launchFragment(view: View) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, ConversationsFragment.newInstance("",""))
        ft.commit()
    }
    fun launchFragmentUser(view: View) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, UserFragment.newInstance("",""))
        ft.commit()
    }
    fun launchFragmentConvInfo(view: View) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, ConversationInfoFragment.newInstance(Conversation()))
        ft.commit()
    }

    fun loadData() {
        /*dbSQLite = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "conversations.db")
            .allowMainThreadQueries() //martelado, need to be fixed
            .build()*/

        gerConversation.getConversations(user.uid,dbSQLite)

    }

    override fun onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onDestroy()
    }

    fun teste(view: View){
        dataConversation = dbSQLite.conversationDao().getAll()

        recyclerView = view.findViewById<RecyclerView>(R.id.fragConversations_conversations_reclist)
        recyclerViewAdapter = UserConvRecAdapter(dataConversation.toMutableList())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun addConv(view: View) {
        launchFragmentConvInfo(this.findViewById(android.R.id.content))
    }

    fun clickConv(conversation : Conversation) {
        Log.d("RECLIST","clickConv.....")

        val intent = Intent(this@HomeActivity, ConversationActivity::class.java)
        intent.putExtra("conversationJSON", Gson().toJson(conversation))
        startActivity(intent)



    }
}