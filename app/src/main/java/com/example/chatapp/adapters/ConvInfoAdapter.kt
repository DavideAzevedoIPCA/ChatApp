package com.example.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.models.User

class ConvInfoAdapter(private val mList: MutableList<User>) : RecyclerView.Adapter<ConvInfoAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvInfoAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ConvInfoAdapterViewHolder(inflater, parent)
    }
    override fun onBindViewHolder(holder: ConvInfoAdapterViewHolder, position: Int) {
        val user = mList.get(position)
        holder.bindData(user)
    }

    fun refreshDate(list : List<User>) {
        this.mList.clear()
        this.mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}