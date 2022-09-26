package com.example.tuchatapplication.chatclasses

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tuchatapplication.R
import com.example.tuchatapplication.adapters.ChatRoomsAdapter
import com.example.tuchatapplication.interfaces.Generalinterface
import com.example.tuchatapplication.models.Group
import com.example.tuchatapplication.models.GroupDisplay
import com.example.tuchatapplication.viewmodels.ChattingViewModel

class NewChatRooms : Fragment(), View.OnClickListener {
    private val TAG = "NewChatRooms"
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var chatRoomsAdapter: ChatRoomsAdapter
    private lateinit var chattingViewModel: ChattingViewModel
    private lateinit var back: RelativeLayout
    private lateinit var search: RelativeLayout
    private lateinit var txtAva: TextView
    private lateinit var generalinterface: Generalinterface
    private lateinit var sharedPreferences: SharedPreferences
    private var newLst: ArrayList<Group>? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chattingViewModel = ViewModelProvider(this).get(ChattingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_chat_rooms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerNewChatRoom)
        back = view.findViewById(R.id.relBackNewChat)
        search = view.findViewById(R.id.relSearch)
        txtAva = view.findViewById(R.id.txtAvailable)
        linearLayoutManager = LinearLayoutManager(activity)
        chatRoomsAdapter = ChatRoomsAdapter(activity as Context)
        txtAva.visibility = View.VISIBLE

        back.setOnClickListener(this)
        search.setOnClickListener(this)

        //Sharedprefences
        sharedPreferences = activity?.getSharedPreferences(getString(R.string.User), Context.MODE_PRIVATE)!!
        userId = sharedPreferences.getString(getString(R.string.id), "")

        chattingViewModel.getGroups(userId!!).observe(viewLifecycleOwner, Observer {
            if (it.size > 0){
                showRecycler(it)
            }
            else{

            }
        })
    }

    private fun showRecycler(it: List<Group>?) {
        var arr: ArrayList<Group> = ArrayList()

        for (i in it!!){
            arr.add(i)
        }

        newLst = arr

        if (newLst!!.size > 0){
            txtAva.visibility = View.GONE
            chatRoomsAdapter.getData(newLst!!)
            recyclerView.adapter = chatRoomsAdapter
            recyclerView.layoutManager = linearLayoutManager
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.relBackNewChat -> {
                generalinterface.goToMainPage()
            }
            R.id.relSearch -> {
                generalinterface.goToSearch()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        generalinterface = context as Generalinterface
    }
}