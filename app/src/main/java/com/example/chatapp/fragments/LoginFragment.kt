package com.example.chatapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.chatapp.IAuthentication
import com.example.chatapp.MainActivity
import com.example.chatapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_PARAM1)
            email = it.getString(ARG_PARAM2)
        }
        Log.d("LOGIN","onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("LOGIN","onCreateView")
        Log.d("LOGIN","$name $email")

        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        val btnLogin: Button = view.findViewById(R.id.fragLogin_login_bt)

        view.findViewById<EditText>(R.id.fragLogin_email_et).setText(email)

        btnLogin.setOnClickListener(View.OnClickListener {
            if (activity is IAuthentication){
                Log.d("LOGIN", "setOnClickListener")
                (activity as MainActivity).loginUser(view)
            }
        })

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Parameter 1.
         * @param email Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String, email: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, name)
                    putString(ARG_PARAM2, email)
                }
            }
    }
}