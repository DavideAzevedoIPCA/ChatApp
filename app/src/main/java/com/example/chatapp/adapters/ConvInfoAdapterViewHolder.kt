package com.example.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.models.User


class ConvInfoAdapterViewHolder(inflater: LayoutInflater, val parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.conversation_info_item, parent, false)) {

    private var tvName: TextView? = itemView.findViewById(R.id.convInfItem_name_tv)
    private var ivImage: ImageView? = itemView.findViewById(R.id.convInfItem_image_iv)

    fun bindData(user: User) {
        tvName?.text = user.name
        //ivImage?.setImageURI()
    }
}