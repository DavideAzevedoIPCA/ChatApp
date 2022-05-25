package com.example.chatapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.chatapp.IAuthentication
import com.example.chatapp.MainActivity
import com.example.chatapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        val btnRegister: Button = view.findViewById(R.id.fragRegister_register_bt)

        btnRegister.setOnClickListener(View.OnClickListener {

//            if (activity is MainActivity){
//                    val editText: EditText = view.findViewById<EditText>(R.id.fragRegister_name_et)
//                    val username = editText.text.toString()
//                val email = view.findViewById<EditText>(R.id.fragRegister_email_et).text.toString()
//                val password = view.findViewById<EditText>(R.id.fragRegister_password_et).text.toString()
//                Log.d("REGISTER", "$username $email $password")
//                (activity as MainActivity).registerUser(username, email, password)
//            }

            if (activity is IAuthentication) {
                    Log.d("Register","frag")
                (activity as MainActivity).registerUser(view)
            }
        }
        )

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}