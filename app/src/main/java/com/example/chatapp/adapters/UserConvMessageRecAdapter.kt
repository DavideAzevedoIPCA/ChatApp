package com.example.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message
import com.example.chatapp.models.User

class UserConvMessageRecAdapter(private val mList: List<Message>, private val conversation: List<User>) : RecyclerView.Adapter<UserConvMessageRecViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserConvMessageRecViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserConvMessageRecViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: UserConvMessageRecViewHolder, position: Int) {
        val message : Message = mList[position]
        holder.bindData(message, conversation)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}