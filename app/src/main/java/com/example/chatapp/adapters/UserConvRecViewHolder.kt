package com.example.chatapp.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.ConversationActivity
import com.example.chatapp.HomeActivity
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

        itemView.setOnClickListener{
            if(it.context is HomeActivity) {
                Log.d("RECLIST", "Arrive")
                (it.context as HomeActivity).clickConv(conversation)
            }

/*                var intent = Intent(it.context, ConversationActivity::class.java)
                //intent.putExtra("user",auth)
                it.context.startActivity(intent)*/

        }

    }



}
