package com.example.chatapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    lateinit var  sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        sharedPreferences = getSharedPreferences("AppSharedPref", MODE_PRIVATE)

        if (FirebaseAuth.getInstance().currentUser != null){
            // User is signed in
            launchHomeActivity()
        }
        else{
            // No user is signed in
            var email : String = sharedPreferences.getString("user_email","email").toString()
            var password : String = sharedPreferences.getString("user_password","password").toString()

            if (!email.isNullOrEmpty()&&!password.isNullOrEmpty()){
                if(authenticateUser(email, password)){
                    launchHomeActivity()
                }
                else{
                    launchLogin(this.findViewById(android.R.id.content))
                }
            }
            else{
                launchRegister(this.findViewById(android.R.id.content))
            }
        }
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
                        .set(User (uid= uid,name = username, email =email, photo_url = ""))
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

        if (authenticateUser(email, password)){
            launchHomeActivity()
        }
    }

    fun launchLogin(view: View) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, LoginFragment.newInstance("",""))
        ft.commit()
    }

    fun launchRegister(view: View) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_placeholder, RegisterFragment.newInstance("",""))
        ft.commit()
    }

    private fun launchHomeActivity(){
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        //intent.putExtra("user",auth)
        startActivity(intent)
    }

    private fun authenticateUser(email : String, password: String) : Boolean{
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    Log.d("LOGIN", "signInWithEmail:success")
                    val user = auth.currentUser

                    // Storing data into SharedPreferences
                    //val sharedPreferences = getSharedPreferences("AppSharedPref", MODE_PRIVATE)
                    // Creating an Editor object to edit(write to the file)
                    val myEdit = sharedPreferences.edit()
                    // Storing the key and its value as the data fetched from edittext
                    myEdit.putString("user_email", email);
                    myEdit.putString("user_password", password);
                    myEdit.commit()
                    //launchHomeActivity()
/*                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    intent.putExtra("user",user)
                    startActivity(intent)*/
                }
                else {
                    Log.d("LOGIN", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        return auth.currentUser != null
    }

}