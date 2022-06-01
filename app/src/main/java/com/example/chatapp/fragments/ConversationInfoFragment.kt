package com.example.chatapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.chatapp.R


private const val NAME = "Artur"
private const val IMG = "Developing App"

class ConversationInfoFragment : Fragment() {
    private var name: String? = null
    private var img: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
             name = it.getString(NAME)
             img = it.getString(IMG)
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_conversation_info, container, false)

        val listView = view.findViewById<ListView>(R.id.fragConvInf_members_lv)

       // val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,name)

        // listView.adapter = adapter

        return view
    }

}