package com.example.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message
import com.example.chatapp.models.User

class UserConvMessageRecAdapter(private var mList: MutableList<Message>, private var mListUser: MutableList<User>, private var user: User) : RecyclerView.Adapter<UserConvMessageRecViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserConvMessageRecViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserConvMessageRecViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: UserConvMessageRecViewHolder, position: Int) {
        val message : Message = mList[position]
        val user : User = mListUser.first { user -> user.uid == message.sentBy }
        holder.bindData(message, user, this.user)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun refreshData(list: List<Message>, listUser: List<User>){
        this.mList.clear()
        this.mList.addAll(list)

        this.mListUser.clear()
        this.mListUser.addAll(listUser)

        notifyDataSetChanged()
    }
}