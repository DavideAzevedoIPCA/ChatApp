package com.example.chatapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ConvInfoAdapter(private val mList: List<String>) : RecyclerView.Adapter<ConvInfoAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvInfoAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ConvInfoAdapterViewHolder(inflater, parent)
    }
    override fun onBindViewHolder(holder: ConvInfoAdapterViewHolder, position: Int) {
        val color = when(position % 2) {
            0 -> android.R.color.holo_red_dark
            1 -> android.R.color.holo_blue_dark
            else -> android.R.color.holo_orange_dark
        }
        val text = mList.get(position) ?: "????"
        holder.bindData(text,color)
    }
    override fun getItemCount(): Int {
        return mList.size
    }
}