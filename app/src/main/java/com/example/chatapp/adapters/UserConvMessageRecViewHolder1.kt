package com.example.chatapp.adapters

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.MyApplication
import com.example.chatapp.R
import com.example.chatapp.models.Message
import com.example.chatapp.models.User
import java.io.File
import java.text.SimpleDateFormat


class UserConvMessageRecViewHolder1(itemView: View) :
    RecyclerView.ViewHolder(itemView){

    private var tvText : TextView? = itemView.findViewById(R.id.convLayoutItem_messageText_textView)
    private var tvDate : TextView? = itemView.findViewById(R.id.convLayoutItem_messageDate_textView)
    private var ivMedia : ImageView? = itemView.findViewById(R.id.convLayoutItem_messageMedia_imageView)

    private var tvText2 : TextView? = itemView.findViewById(R.id.convLayoutItem_messageText_textView2)
    private var tvDate2 : TextView? = itemView.findViewById(R.id.convLayoutItem_messageDate_textView2)
    private var ivMedia2 : ImageView? = itemView.findViewById(R.id.convLayoutItem_messageMedia_imageView2)


    //private var imgUri: Uri = Uri.parse("https://this-person-does-not-exist.com/img/avatar-d8b75b7d82474a0f5353797a92bfa8ab.jpg" )

    fun bindData(message: Message, user: User, myUser : User){
        //iv?.setImageURI(imgUri)

        if (myUser.uid == message.sentBy){ //type 1
            //itemView.setBackgroundColor(Color.parseColor("#D9FDD3"))

/*            if (message.media_url.isNotEmpty()&&message.media_url!="null"){
                val uri : Uri = Uri.parse(message.media_url)
                ivMedia?.setImageURI(uri)
            }*/

/*            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            var image: Bitmap? = null

            executor.execute {
                val imageURL = message.media_url
                try {
                    val `in` = java.net.URL(imageURL).openStream()
                    image = BitmapFactory.decodeStream(`in`)

                    // Only for making changes in UI
                    handler.post {
                        ivMedia?.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }*/

            if (message.media_url.length > 0&&message.media_url != "null"){

                val path = MyApplication.applicationContext().getExternalFilesDir("")
                val imgFile = File(path?.path,"/images/"+message.media_url+".jpg")

                if (imgFile.exists()){
                    val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    ivMedia?.setImageBitmap(myBitmap)
                }
            }else{
                ivMedia?.setImageBitmap(null)
            }

            tvText?.text = message.text
            tvDate?.text = SimpleDateFormat("EEEE, dd MMMM, yyyy, HH:mm:ss").format(message.sendAt)


        }else{ //type 2
            //itemView.setBackgroundColor(Color.parseColor("#F0F0F0"))

            if (message.media_url.length > 0&&message.media_url != "null"){

                val path = MyApplication.applicationContext().getExternalFilesDir("")
                val imgFile = File(path?.path,"/images/"+message.media_url+".jpg")

                if (imgFile.exists()){
                    val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    ivMedia?.setImageBitmap(myBitmap)
                }
            }else{
                ivMedia?.setImageBitmap(null)
            }

            tvText2?.text = message.text
            tvDate2?.text = SimpleDateFormat("EEEE, dd MMMM, yyyy, HH:mm:ss").format(message.sendAt)
        }
    }

}
