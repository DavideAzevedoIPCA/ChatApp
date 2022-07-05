package com.example.chatapp.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.MyApplication
import com.example.chatapp.R
import com.example.chatapp.models.User
import java.io.File


class ConvInfoAdapterViewHolder(inflater: LayoutInflater, val parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.conversation_info_item, parent, false)) {

    private var tvName: TextView? = itemView.findViewById(R.id.convInfItem_name_tv)
    private var ivImage: ImageView? = itemView.findViewById(R.id.convInfItem_image_iv)

    fun bindData(user: User) {
        tvName?.text = user.name

        if (user.photo_url.isNotEmpty() && user.photo_url != "null"){
            val path = MyApplication.applicationContext().getExternalFilesDir("")
            val imgFile = File(path?.path,"/images/"+user.photo_url+".jpg")

            if (imgFile.exists()){
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                ivImage?.setImageBitmap(myBitmap)
            }
        }else{
            ivImage?.setImageBitmap(null)
        }
    }
}
