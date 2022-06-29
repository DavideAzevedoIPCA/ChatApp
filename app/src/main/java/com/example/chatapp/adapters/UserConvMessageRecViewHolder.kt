package com.example.chatapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.Message


class UserConvMessageRecViewHolder(inflater: LayoutInflater, val parent : ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.message_layout_item,parent,false)){

    private var tvTitle : TextView? = itemView.findViewById(R.id.convLayoutItem_convTitle_textView)
    private var tvText : TextView? = itemView.findViewById(R.id.convLayoutItem_convText_textView)

    //private var imgUri: Uri = Uri.parse("https://this-person-does-not-exist.com/img/avatar-d8b75b7d82474a0f5353797a92bfa8ab.jpg" )

    fun bindData(message: Message, conversation: Conversation){
        //iv?.setImageURI(imgUri)
        tvTitle?.text = message.text
        tvText?.text = message.sendAt?.toString()
    }
}
