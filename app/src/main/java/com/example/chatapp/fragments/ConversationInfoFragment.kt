package com.example.chatapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapters.ConvInfoAdapter
import com.example.chatapp.models.Conversation
import com.example.chatapp.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConversationInfoFragment : Fragment() {
    private lateinit var conversation : Conversation
    private var name: String? = null
    private var img: String? = null
    private var listUsers: MutableList<User> = mutableListOf()

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //name = it.getString(ARG_PARAM1)
            //img = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_conversation_info, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.fragConvInf_members_rv)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter= ConvInfoAdapter(listUsers)

        val back_btn = view.findViewById<ImageButton>(R.id.fragconvInf_back_bt)

        val plus_btn = view.findViewById<ImageButton>(R.id.fragConvInf_plus_bt)

        val ConvInfoTitle = view.findViewById<TextView>(R.id.fragConvInf_title_tv)
        val add_et = view.findViewById<EditText>(R.id.fragConvInf_add_et)
        val add_bt = view.findViewById<Button>(R.id.fragConvInf_add_bt)

        ConvInfoTitle.text = conversation.title


        back_btn.setOnClickListener {

            activity?.onBackPressed();
            // needs bugfixing
        }

        plus_btn.setOnClickListener {
            if( add_et.visibility == View.GONE )
            {
                add_et.visibility = View.VISIBLE
                add_bt.visibility= View.VISIBLE
            }
            else if ( add_et.visibility == View.VISIBLE )
            {
                add_et.visibility = View.GONE
                add_bt.visibility = View.GONE
            }
        }

        add_bt.setOnClickListener {

            val new_member = add_et.text.toString()
            db.collection("users")
                .whereEqualTo("email", new_member)
                .get().addOnSuccessListener { values ->
                    var user : User = User()
                    values?.forEach{
                        user = User()

                        user.uid = it.data["uid"].toString()
                        user.name = it.data["name"].toString()
                        user.email = it.data["email"].toString()
                        user.photo_url = it.data["photo_url"].toString()

                        GlobalScope.launch {
                            listUsers.add(user)
                        }
                            if(recyclerView.adapter is ConvInfoAdapter){
                                (recyclerView.adapter as ConvInfoAdapter).refreshDate(listUsers)
                            }

                    }
                }

            Log.d("New Member", new_member)
            Log.d("Conversation info", conversation.toString())
            Log.d("Conversation id", conversation.id)
            Log.d("Conversation title", conversation.title)
            Log.d("Conversation users", conversation.users.toString())
            Log.d("Conversation LastMessag", conversation.lastMessage.toString())

        }



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
        fun newInstance(conv: Conversation) =
            ConversationInfoFragment().apply {
                arguments = Bundle().apply {
                    conversation = conv
                }
            }
    }
}