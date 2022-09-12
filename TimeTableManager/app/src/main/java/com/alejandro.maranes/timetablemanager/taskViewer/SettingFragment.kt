package com.alejandro.maranes.timetablemanager.taskViewer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import com.alejandro.maranes.timetablemanager.GlobalVariables
import com.alejandro.maranes.timetablemanager.R

class SettingFragment : Fragment() {

    private lateinit var notifSwitch:SwitchCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val notiff = sharedPref.getBoolean(("NOTIFICATION"),true)

        GlobalVariables.notif=notiff

        notifSwitch=view.findViewById(R.id.notificationSwitch)
        notifSwitch.isChecked=GlobalVariables.notif

        notifSwitch.setOnClickListener{
            with (sharedPref.edit()) {
                putBoolean("NOTIFICATION",notifSwitch.isChecked)
                apply()
            }
            GlobalVariables.notif=notifSwitch.isChecked
        }




    }


}