package com.example.tuchatapplication.chatclasses

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tuchatapplication.R
import com.example.tuchatapplication.adapters.ChatRoomsAdapter
import com.example.tuchatapplication.adapters.GroupsAdapter
import com.example.tuchatapplication.interfaces.Generalinterface
import com.example.tuchatapplication.models.GroupDisplay
import com.example.tuchatapplication.viewmodels.ChattingViewModel

class Search : Fragment(), View.OnClickListener {
    private lateinit var search: EditText
    private lateinit var back: RelativeLayout
    private lateinit var generalinterface: Generalinterface
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var groupsAdapter: GroupsAdapter
    private lateinit var chattingViewModel: ChattingViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chattingViewModel = ViewModelProvider(requireActivity()).get(ChattingViewModel::class.java)
        search = view.findViewById(R.id.editSearch)
        back = view.findViewById(R.id.relBackSearch)
        recyclerView = view.findViewById(R.id.recSearch)
        linearLayoutManager = LinearLayoutManager(activity)
        groupsAdapter = GroupsAdapter(activity as Context)

        back.setOnClickListener(this)

        //Sharedprefences
        sharedPreferences = activity?.getSharedPreferences(getString(R.string.User), Context.MODE_PRIVATE)!!
        userId = sharedPreferences.getString(getString(R.string.id), "")

        searchItems()

        getGroups()
    }

    private fun getGroups() {
        chattingViewModel.getMemberGroups(userId!!).observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                showRecycler(it)
            }
        })
    }

    private fun showRecycler(it: List<GroupDisplay>?) {
        groupsAdapter.getData(it as ArrayList<GroupDisplay>)
        recyclerView.adapter = groupsAdapter
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun searchItems() {
        search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                groupsAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.relBackSearch -> {
                generalinterface.goToMainPage()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        generalinterface = context as Generalinterface
    }
}