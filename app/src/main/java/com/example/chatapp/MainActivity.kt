package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.chatapp.fragments.LoginFragment
import com.example.chatapp.fragments.RegisterFragment
import com.example.chatapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), IAuthentication {

    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, RegisterFragment.newInstance("a","b"))
        ft.commit()
    }

    override fun registerUser(v: View){

        val username = v.findViewById<EditText>(R.id.fragRegister_name_et).text.toString()
        val email = v.findViewById<EditText>(R.id.fragRegister_email_et).text.toString()
        val password = v.findViewById<EditText>(R.id.fragRegister_password_et).text.toString()

        Log.d("REGISTER","$username $email $password")
        Log.d("REGISTER", "registerUser")
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if (task.isSuccessful) {
                    val uid = task.result.user?.uid

                    db.collection("users").document(uid!!)
                        .set(User (uid= uid,name = username, photo_url = ""))
                        .addOnSuccessListener {
                            Log.d("REGISTER","Successfully Register ")

                            runOnUiThread {
                                this.endRegister(username, email)
                            }
                            //finish()
                        }
                }
                else{
                    Log.d("REGISTER","unsuccessfully $task.exception")
                    Toast.makeText(this,getString(R.string.msg_register_error), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun endRegister(name: String, email: String){

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, LoginFragment.newInstance(name,email))
        ft.commit()
    }

    override fun loginUser(v: View) {
        val email = v.findViewById<EditText>(R.id.fragLogin_email_et).text.toString()
        val password = v.findViewById<EditText>(R.id.fragLogin_password_et).text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    Log.d("LOGIN", "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Log.d("LOGIN", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun endLogin(user: User){

    }


}