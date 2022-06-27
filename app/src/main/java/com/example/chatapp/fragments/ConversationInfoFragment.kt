package com.example.chatapp.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.ConversationActivity
import com.example.chatapp.R
import com.example.chatapp.adapters.ConvInfoAdapter
import com.example.chatapp.models.Conversation
import com.google.firebase.firestore.FirebaseFirestore


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConversationInfoFragment : Fragment() {
    private lateinit var conversation : Conversation
    private var name: String? = null
    private var img: String? = null

    val db = FirebaseFirestore.getInstance()

    //TODO RECEIVE CONVERSATION UID FROM ACTIVITY
    val uid = "dwee2312313"

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

        val back_btn = view.findViewById<ImageButton>(R.id.fragconvInf_back_bt)

        val plus_btn = view.findViewById<ImageButton>(R.id.fragConvInf_plus_bt)

        val add_et = view.findViewById<EditText>(R.id.fragConvInf_add_et)
        val add_bt = view.findViewById<Button>(R.id.fragConvInf_add_bt)

        back_btn.setOnClickListener {

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
            val users = db.collection("users")
            val conversations = db.collection("conversations")
            val new_member = add_et.text.toString()

            val query = users.whereEqualTo("email", new_member)

            Log.d("Query Result", query.toString())

                if (query.toString() != null) {
                    add_et.visibility = View.GONE
                    add_bt.visibility = View.GONE
                    Toast.makeText(activity, getString(R.string.fragConvInfo_user_added), Toast.LENGTH_LONG).show()
                    //TODO ALTERAR LINHA ABAIXO PARA RECEBER UID CORRETAMENTE E ALTERAR CORRETAMENTE OS UTILIZADORES
                    conversations.document(uid).update("users", query)
                }
        }

        view.findViewById<RecyclerView>(R.id.fragConvInf_members_rv).layoutManager = LinearLayoutManager(activity)
        view.findViewById<RecyclerView>(R.id.fragConvInf_members_rv).adapter= ConvInfoAdapter((listOf("A","B","C","D","E","F","G","H","I","J")))

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