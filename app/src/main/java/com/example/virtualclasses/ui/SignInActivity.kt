package com.example.virtualclasses.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.virtualclasses.R
import com.example.virtualclasses.utils.Communicator

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    override fun onBackPressed() {
        if(!Communicator.isFirebaseLoading){
            super.onBackPressed()
        }
    }
}