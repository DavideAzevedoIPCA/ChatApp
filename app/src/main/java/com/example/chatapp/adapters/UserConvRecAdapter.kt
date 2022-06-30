package com.example.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.models.Conversation

class UserConvRecAdapter(private var mList: MutableList<Conversation>) : RecyclerView.Adapter<UserConvRecViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserConvRecViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserConvRecViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: UserConvRecViewHolder, position: Int) {
        val conv : Conversation = mList[position]
        holder.bindData(conv)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun refreshData(item: List<Conversation>){
        this.mList.clear()
        this.mList.addAll(item)
        notifyDataSetChanged()
    }
}