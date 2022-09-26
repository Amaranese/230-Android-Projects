package com.example.tuchatapplication.chatclasses

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tuchatapplication.R
import com.example.tuchatapplication.interfaces.Generalinterface

class Profile : Fragment(), View.OnClickListener {
    private lateinit var generalinterface: Generalinterface
    private lateinit var relLog: RelativeLayout
    private lateinit var back: RelativeLayout
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var email: TextView
    private lateinit var phone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        relLog = view.findViewById(R.id.logOut)
        back = view.findViewById(R.id.relBackProf)
        firstName = view.findViewById(R.id.firstN)
        lastName = view.findViewById(R.id.lastN)
        email = view.findViewById(R.id.em)
        phone = view.findViewById(R.id.phoneProf)
        relLog.setOnClickListener(this)
        back.setOnClickListener(this)

        setProfile()
    }

    private fun setProfile() {
        var sharedPreferences = activity?.getSharedPreferences(getString(R.string.User),
            Context.MODE_PRIVATE
        )
        firstName.text = sharedPreferences!!.getString(getString(R.string.firstName), "")
        lastName.text = sharedPreferences.getString(getString(R.string.lastName), "")
        email.text = sharedPreferences.getString(getString(R.string.email), "")
        phone.text = sharedPreferences.getString(getString(R.string.phone), "")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        generalinterface = context as Generalinterface
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.logOut -> {
                generalinterface.logOut()
            }
            R.id.relBackProf -> {
                generalinterface.goToMainPage()
            }
        }
    }
}