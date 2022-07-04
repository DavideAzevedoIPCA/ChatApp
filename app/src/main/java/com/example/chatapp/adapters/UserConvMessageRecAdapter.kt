package com.example.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.models.Message
import com.example.chatapp.models.User

class UserConvMessageRecAdapter(private var mList: MutableList<Message>,
                                private var mListUser: MutableList<User>,
                                private var user: User) : RecyclerView.Adapter<UserConvMessageRecViewHolder1>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserConvMessageRecViewHolder1 {
        val layout = when(viewType){
            1 -> R.layout.message_layout_item
            2 -> R.layout.message_layout_item2
            else -> R.layout.message_layout_item
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layout,parent,false)
        return UserConvMessageRecViewHolder1(view)
        //return UserConvMessageRecViewHolder(inflater, parent)
    }

    override fun getItemViewType(position: Int): Int{
        return if(mList[position].sentBy == user.uid) {
            1
        } else {
            2
        }
        //return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: UserConvMessageRecViewHolder1, position: Int) {
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

enum class ViewType(val value: Int){
    MSG_BY_ME(1), MSG_BY_OTHER(2)
}
