package com.example.chatapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message
import com.example.chatapp.models.User
import java.text.DateFormat
import java.text.SimpleDateFormat


class UserConvMessageRecViewHolder(inflater: LayoutInflater, val parent : ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.message_layout_item,parent,false)){

    private var tvText : TextView? = itemView.findViewById(R.id.convLayoutItem_messageText_textView)
    private var tvDate : TextView? = itemView.findViewById(R.id.convLayoutItem_messageDate_textView)

    //private var imgUri: Uri = Uri.parse("https://this-person-does-not-exist.com/img/avatar-d8b75b7d82474a0f5353797a92bfa8ab.jpg" )

    fun bindData(message: Message, user: User, myUser : User){
        //iv?.setImageURI(imgUri)

        if (myUser.uid == message.sentBy){
            tvText?.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
            tvDate?.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        }else{
            tvText?.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
            tvDate?.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        }

        tvText?.text = message.text
        tvDate?.text = SimpleDateFormat("EEEE, dd MMMM, yyyy, HH:mm:ss").format(message.sendAt)
    }
}
