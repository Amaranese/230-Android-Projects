package com.alejandro.maranes.tuchatapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alejandro.maranes.tuchatapplication.apputils.AppUtils
import com.alejandro.maranes.tuchatapplication.auth.Login
import com.alejandro.maranes.tuchatapplication.chatclasses.ChatRoom
import com.alejandro.maranes.tuchatapplication.chatclasses.Dashboard
import com.alejandro.maranes.tuchatapplication.interfaces.Generalinterface
import com.alejandro.maranes.tuchatapplication.models.Group
import com.alejandro.maranes.tuchatapplication.models.Member
import com.alejandro.maranes.tuchatapplication.viewmodels.ChattingViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), Generalinterface {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG = "MainActivity"
    private lateinit var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var chattingViewModel: ChattingViewModel
    private lateinit var appUtils: AppUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.navFrag) as NavHostFragment
        navController = navHostFragment.navController
        chattingViewModel = ViewModelProvider(this).get(ChattingViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        appUtils = AppUtils(this)
        initFrag()
    }

    private fun initFrag() {
        navController.navigate(R.id.dashboard)
    }

    //get phone details
    override fun getPhoneDetails(phone: String): String {
        var resultCode: String? = null

        mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                var code = p0.smsCode

                if (code != null){
                    resultCode = code
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.i(TAG, "onVerificationFailed: $p0")

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                Log.i(TAG, "onCodeSent: $p0")

            }
        }

        val phoneAuthOptions: PhoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBack)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)

        return resultCode!!
    }

    override fun goToProfile() {
        navController.navigate(R.id.profile)
    }

    override fun goToNewChatRooms() {
        navController.navigate(R.id.newChatRooms)
    }

    override fun goToSearch() {
        navController.navigate(R.id.search)
    }

    override fun logOut() {
        sharedPreferences = this.getSharedPreferences(getString(R.string.User), MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
        startActivity(Intent(this, Login::class.java))
        finish()
    }

    override fun addChatRoom(group: Group) {
        if (appUtils.checkWiFi()){
            var sharedPreferences2 = getSharedPreferences("USER", Context.MODE_PRIVATE)
            var userPhone = sharedPreferences2.getString(getString(R.string.phone), "")

            mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    var code = p0.smsCode

                    if (code != null){
                        var newSharedPreferences: SharedPreferences = getSharedPreferences("CODE", MODE_PRIVATE)
                        var editor2: SharedPreferences.Editor = newSharedPreferences.edit()
                        editor2.putString("NUMBER", code)
                        editor2.putString("GroupId", group.group_id)
                        editor2.apply()

                        navController.navigate(R.id.verification)
                    }

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Log.i(TAG, "onVerificationFailed: $p0")

                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    Log.i(TAG, "onCodeSent: $p0")

                }
            }

            val phoneAuthOptions: PhoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+254$userPhone")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallBack)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
        }
        else{
            Toast.makeText(this, "Connect to the internet for code generation", Toast.LENGTH_LONG).show()
        }

    }

    override fun goToMainPage() {
        navController.navigate(R.id.dashboard)
    }

    override fun goToChatPage(groupId: String) {
        var sharedPrefs: SharedPreferences = getSharedPreferences("GROUPID", MODE_PRIVATE)
        var ed: SharedPreferences.Editor = sharedPrefs.edit()
        ed.putString("groupId", groupId)
        ed.apply()

        navController.navigate(R.id.chatRoom)
    }

    override fun onBackPressed() {
        navHostFragment.childFragmentManager.primaryNavigationFragment?.let {
            if (it is ChatRoom){
                navController.navigate(R.id.dashboard)
            }
            if (it is Dashboard){
                logOut()
            }
            else{
                super.onBackPressed()
            }
        }
    }
}