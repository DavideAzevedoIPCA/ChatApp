package com.example.chatapp.utils

import android.util.Log
import com.example.chatapp.MyApplication
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class MediaUtils {

    val storage = Firebase.storage("gs://chatapp-d77ab.appspot.com")

    public fun dowloadMedia(fileName : String){
        val storageRef = storage.reference
        val pathRef = storageRef.child("images/$fileName")

        val path = MyApplication.applicationContext().getExternalFilesDir("")

        val rootPath = File(path?.path,"/images")
        if (!rootPath.exists()) {
            rootPath.mkdirs()
        }

        val localFile : File = File(rootPath, "$fileName.jpg")

        pathRef.getFile(localFile)
            .addOnSuccessListener {
                Log.d("DOWNLOAD", "Success ${localFile.toString()}")
            }
            .addOnFailureListener{
                Log.d("DOWNLOAD","Failure")
            }
    }
}