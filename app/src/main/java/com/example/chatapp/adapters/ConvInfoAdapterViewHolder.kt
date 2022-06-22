package com.example.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R

class ConvInfoAdapterViewHolder(inflater: LayoutInflater, val parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.conversation_info_item, parent, false)) {
    private var tv: TextView? = itemView.findViewById(R.id.convInfItem_name_tv)
    private var iv: ImageView? = itemView.findViewById(R.id.convInfItem_image_iv)
    fun bindData(text: String, colorResource: Int) {
        tv?.text = text
        iv?.setBackgroundResource(colorResource)
        itemView.setOnClickListener {
            Toast.makeText(parent.context,text,Toast.LENGTH_LONG).show()
        }
    }
}