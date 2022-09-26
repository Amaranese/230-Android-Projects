package com.example.tuchatapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tuchatapplication.MainActivity
import com.example.tuchatapplication.R
import com.google.android.material.button.MaterialButton

class CodeVerification : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_codeverification)
        initViews()
    }

    private fun initViews() {
        btn = findViewById(R.id.btnVerify)
        btn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnVerify -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}