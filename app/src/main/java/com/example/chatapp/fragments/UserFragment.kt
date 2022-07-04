package com.example.chatapp.fragments


import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.chatapp.GerUser
import com.example.chatapp.HomeActivity
import com.example.chatapp.MyApplication
import com.example.chatapp.R
import com.example.chatapp.models.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import java.io.File
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var user: User = User()
    private lateinit var etName : EditText
    private lateinit var tvEmail : TextView
    private lateinit var ivPhoto : ImageView
    private lateinit var pbSave : Button
    private var gerUser : GerUser = GerUser()
    private var storage = Firebase.storage("gs://chatapp-d77ab.appspot.com")
    private var photoUri : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = Gson().fromJson(it.getString(ARG_PARAM1), User::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_user, container, false)

        etName = view.findViewById(R.id.fragUserInfo_name_tx)
        tvEmail = view.findViewById(R.id.fragUserInfo_email_tx)
        ivPhoto = view.findViewById(R.id.fragUserInfo_photo_iv)
        pbSave = view.findViewById(R.id.fragUserInfo_save_btn)

        etName.setText(user.name)
        tvEmail.text = user.email

        if (user.photo_url.length > 0){
            val path = MyApplication.applicationContext().getExternalFilesDir("")
            val imgFile = File(path?.path,"/images/"+user.photo_url+".jpg")

            if (imgFile.exists()){
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                ivPhoto?.setImageBitmap(myBitmap)
            }else{
                ivPhoto?.setImageBitmap(null)
            }
        }else{
            ivPhoto.setImageDrawable(resources.getDrawable(R.drawable.unknown_person))
        }

        ivPhoto.setOnClickListener(View.OnClickListener {

            if (activity is HomeActivity){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 73)

            }
        })

        pbSave.setOnClickListener(View.OnClickListener {

            if (activity is HomeActivity){
                user.name = etName?.text.toString()

                if (photoUri.length > 0&&photoUri != user.photo_url){

                    var nameTo = UUID.randomUUID().toString()
                    var storageRef = storage.reference
                    var imageRef : StorageReference? = storageRef.child("images/"+nameTo)

                    if (imageRef != null) {
                        imageRef.putFile(photoUri.toUri())
                            .addOnCompleteListener{ task ->
                                if (task.isSuccessful){
                                    Log.d("UPLOAD", "Sucess")
                                    Toast.makeText(context,"Success", Toast.LENGTH_LONG).show()

                                    user.photo_url = nameTo
                                    context?.let { it1 -> gerUser.updateUser(user, it1) }
                                    photoUri = ""
                                }else{
                                    Log.d("UPLOAD", "Failure")
                                    Toast.makeText(context,"Failed " + task.toString(), Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                }else{
                    context?.let { it1 -> gerUser.updateUser(user, it1) }
                }
            }
        })



        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 73){
            ivPhoto.setImageURI(data?.data as Uri)
            photoUri = (data?.data as Uri).toString()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(user: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, user)
                }
            }
    }
}