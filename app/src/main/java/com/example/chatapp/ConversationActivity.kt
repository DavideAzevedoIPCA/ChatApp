package com.example.chatapp

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
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
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
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
    var storage = Firebase.storage("gs://chatapp-d77ab.appspot.com")

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1){

            uploadMedia(data?.data as Uri)
            //addMessage(findViewById(android.R.id.content),data?.data)
            //imageView.setImageURI(data?.data) // handle chosen image
        }
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

    fun addMessage(view: View){
        if (editTextMessage.text.toString().isNotEmpty()) {
            var message = Message(
                "",
                user.uid,
                Date(),
                editTextMessage.text.toString(),
                "",
                MessageState.NONE,
                conversation.id
            )
            editTextMessage.text?.clear()
            message.id = gerMessage.setMessage(message, conversation)

            dbSQLite.messageDao().insertMessage(message)
            dataMessage.add(message)
            (recyclerView.adapter as UserConvMessageRecAdapter).refreshData(dataMessage, dataUsers)
            recyclerView.smoothScrollToPosition(recyclerView.bottom)
        }
    }

    fun addMessage(view: View, uri: Uri?) {

            var message = Message(
                "",
                user.uid,
                Date(),
                editTextMessage.text.toString(),
                "",
                MessageState.NONE,
                conversation.id
            )

            if (uri != null) {
                message.media_url = uri.toString()
            }

            editTextMessage.text?.clear()
            message.id = gerMessage.setMessage(message, conversation)

            dbSQLite.messageDao().insertMessage(message)
            dataMessage.add(message)
            (recyclerView.adapter as UserConvMessageRecAdapter).refreshData(dataMessage, dataUsers)
            recyclerView.smoothScrollToPosition(recyclerView.bottom)
    }

    fun openImage(view: View){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)

    }

    fun uploadMedia(uri: Uri){
        var nameTo = UUID.randomUUID().toString()
        var storageRef = storage.reference
        var imageRef : StorageReference? = storageRef.child("images/"+nameTo)

        if (imageRef != null) {
            imageRef.putFile(uri)
                /*.addOnSuccessListener { snap ->
                    Log.d("UPLOAD", "Sucess")
                    Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{ e ->
                    Log.d("UPLOAD", "Failure")
                    Toast.makeText(this,"Failed " + e.message,Toast.LENGTH_LONG).show()
                }*/
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        Log.d("UPLOAD", "Sucess")
                        Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()

                        addMessage(findViewById(android.R.id.content),nameTo.toUri())

                    }else{
                        Log.d("UPLOAD", "Failure")
                        Toast.makeText(this,"Failed " + task.toString(),Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}