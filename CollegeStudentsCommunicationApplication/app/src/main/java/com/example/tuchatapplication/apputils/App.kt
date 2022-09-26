package com.example.tuchatapplication.apputils

import android.app.Application
import android.widget.Toast
import com.example.tuchatapplication.R
import com.google.firebase.auth.FirebaseAuth

class App: Application() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate() {
        super.onCreate()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(getString(R.string.app_email), getString(R.string.app_password)).addOnCompleteListener {
            if (it.isSuccessful){

            }
        }

    }
}