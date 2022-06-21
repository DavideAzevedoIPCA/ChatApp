package com.example.chatapp.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.ConversationActivity
import com.example.chatapp.R
import com.example.chatapp.adapters.ConvInfoAdapter


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConversationInfoFragment : Fragment() {
    private var name: String? = null
    private var img: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
             name = it.getString(ARG_PARAM1)
             img = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
                            ): View? {
        val view = inflater.inflate(R.layout.fragment_conversation_info, container, false)

        val back_btn = view.findViewById<ImageButton>(R.id.fragconvInf_back_bt)

        val plus_btn = view.findViewById<ImageButton>(R.id.fragConvInf_plus_bt)

        val add_et = view.findViewById<EditText>(R.id.fragConvInf_add_et)

        back_btn.setOnClickListener {

        }

        plus_btn.setOnClickListener {
            if( add_et.visibility == View.GONE )
            {
                add_et.visibility = View.VISIBLE
            }
            else if ( add_et.visibility == View.VISIBLE )
            {
                add_et.visibility = View.GONE
            }
        }

        add_et.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                /*
                    if ( add_et.text == img ) {
                        add_et.visibility = View.GONE
                        Toast.makeText(this,getString(R.string.fragConvInfo_user_added), Toast.LENGTH_LONG).show()
                    }
                        else  {
                        Toast.makeText(this,getString(R.string.fragConvInfo_user_not_found), Toast.LENGTH_LONG).show()
                    }
                */

                return@OnKeyListener true
            }
            false
        })

        view.findViewById<RecyclerView>(R.id.fragConvInf_members_rv).layoutManager = LinearLayoutManager(activity)
        view.findViewById<RecyclerView>(R.id.fragConvInf_members_rv).adapter= ConvInfoAdapter((listOf("A","B","C","D","E","F","G","H","I","J")))

        return view
    }

}