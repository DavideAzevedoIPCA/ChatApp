package com.example.chatapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.room.Room
import com.example.chatapp.fragments.ConversationsFragment
import com.example.chatapp.fragments.LoginFragment
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message
import com.example.chatapp.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {

    var user: User = User("","","")
    private lateinit var auth: FirebaseAuth
    val gerConversation : GerConversation = GerConversation()
    val gerMessage : GerMessage = GerMessage()
    private lateinit var dbSQLite : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        user.uid = auth.currentUser!!.uid.toString()
        user.name = auth.currentUser!!.email.toString()
        Log.d("HOME", "user_uid:"+user.uid.toString())
        Log.d("HOME", "user_name:"+user.name.toString())

        if (checkForInternet(this)){

            LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, IntentFilter("custom-event-name"))

            loadData() //faz loading dos dados do firestore para SQLite

        }

        GlobalScope.launch {
            val dataConversation = dbSQLite.conversationDao().getAll()
            val dataMessage = dbSQLite.messageDao().getAll()
        }

        var conv : Conversation = Conversation("","conversa teste", listOf(user.uid,"qweqweq"))
        gerConversation.setConversation(conv)
    }

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val message = intent.getStringExtra("message")
            Log.d("receiver", "Got message: $message")
        }
    }


    fun launchFragment(view: View) {}
    fun launchFragmentUser(view: View) {}

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun loadData()
    {
        dbSQLite = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "conversations.db")
            .build()

        gerConversation.getConversations(user.uid,dbSQLite)

        val dataMessage : List<Message>? = gerMessage.getMessages(user.uid)
        dataMessage?.forEach {
            dbSQLite.messageDao().insertMessage(it)
        }
    }

    fun getConversations(){

    }

    fun addConversation(){

    }

    fun launchConversations(view: View) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, ConversationsFragment.newInstance("",""))
        ft.commit()
    }

    override fun onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        super.onDestroy()
    }
}