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
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.tuchatapplication.R
import com.example.tuchatapplication.interfaces.Generalinterface
import com.example.tuchatapplication.models.Member
import com.example.tuchatapplication.viewmodels.ChattingViewModel
import com.google.android.material.button.MaterialButton
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class Verification : Fragment(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var phone: TextView
    private lateinit var numOne: EditText
    private lateinit var numTwo: EditText
    private lateinit var numThree: EditText
    private lateinit var numFour: EditText
    private lateinit var numFive: EditText
    private lateinit var numSix: EditText
    private lateinit var btn: MaterialButton
    private lateinit var resend: TextView
    private lateinit var chattingViewModel: ChattingViewModel
    private var code: String? = null
    private var groupId: String? = null
    private var user: String? = null
    private var userPhone: String? = null
    private lateinit var generalinterface: Generalinterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chattingViewModel = ViewModelProvider(requireActivity()).get(ChattingViewModel::class.java)

        phone = view.findViewById(R.id.phoneNumber)
        numOne = view.findViewById(R.id.editOne)
        numTwo = view.findViewById(R.id.editTwo)
        numThree = view.findViewById(R.id.editThree)
        numFour = view.findViewById(R.id.editFour)
        numFive = view.findViewById(R.id.editFive)
        numSix = view.findViewById(R.id.editSix)
        btn = view.findViewById(R.id.btnCode)
        resend = view.findViewById(R.id.resend)

        btn.setOnClickListener(this)
        resend.setOnClickListener(this)

        sharedPreferences = activity?.getSharedPreferences("CODE", Context.MODE_PRIVATE)!!
        code = sharedPreferences.getString("NUMBER", "")
        groupId = sharedPreferences.getString("GroupId", "")

        //user
        var sharedPreferences2 = activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
        user = sharedPreferences2!!.getString(getString(R.string.id), "")
        userPhone = sharedPreferences2.getString(getString(R.string.phone), "")

        phone.text = "+254${userPhone}"

        textChanging()
    }

    private fun textChanging() {
        numOne.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                numTwo.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        } )
        numTwo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                numThree.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        } )
        numThree.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                numFour.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        } )
        numFour.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                numFour.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        } )
        numFour.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                numFive.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        } )
        numFive.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                numSix.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        } )
    }

    private fun sendDetails() {
        var member: Member = Member()
        member.groupId = groupId
        member.dateAdded = SimpleDateFormat("yyyy-MM-dd").format(Date())
        member.code = code
        member.userId = user

        var response = chattingViewModel.addMember(member)
        if (response >= 0){
            generalinterface.goToChatPage(groupId!!)
            Toast.makeText(activity, "Added", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(activity, "Not Added", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnCode -> {
                checkDetails()
            }
            R.id.resend -> {
                resendCode()
            }
        }
    }

    private fun resendCode() {
        var result = generalinterface.getPhoneDetails(userPhone!!)
        if (result != null){
            code = result
        }
    }

    private fun checkDetails() {
        var one = numOne.text.toString().trim()
        var two = numTwo.text.toString().trim()
        var three = numThree.text.toString().trim()
        var four = numFour.text.toString().trim()
        var five = numFive.text.toString().trim()
        var six = numSix.text.toString().trim()

        var stringBuilder: StringBuilder = StringBuilder()

        if (one != "" || two != "" || three != "" || four != "" || five != "" || six != ""){
            stringBuilder.append(one)
            stringBuilder.append(two)
            stringBuilder.append(three)
            stringBuilder.append(four)
            stringBuilder.append(five)
            stringBuilder.append(six)

            if (stringBuilder.toString() == code){
                sendDetails()
            }
            else{
                Toast.makeText(activity, "Invalid Code", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(activity, "Missing Number", Toast.LENGTH_LONG).show()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        generalinterface = context as Generalinterface
    }
}