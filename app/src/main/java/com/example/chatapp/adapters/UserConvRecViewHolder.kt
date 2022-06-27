package com.example.chatapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.models.Conversation


class UserConvRecViewHolder(inflater: LayoutInflater, val parent : ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.conversation_layout_item,parent,false)){

    private var iv : ImageView? = itemView.findViewById(R.id.convLayoutItem_convPhoto_imageView)
    private var tvTitle : TextView? = itemView.findViewById(R.id.convLayoutItem_convTitle_textView)
    private var tvText : TextView? = itemView.findViewById(R.id.convLayoutItem_convText_textView)

    //private var imgUri: Uri = Uri.parse("https://this-person-does-not-exist.com/img/avatar-d8b75b7d82474a0f5353797a92bfa8ab.jpg" )

    fun bindData(conversation : Conversation){
        //iv?.setImageURI(imgUri)
        tvTitle?.text = conversation.title
        tvText?.text = conversation.lastMessage?.text
    }
}
