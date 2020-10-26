package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireStore
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener(){
        registerButton.setOnClickListener {
            val name = registerName.text.toString().trim()
            val email = registerEmail.text.toString().trim()
            val password = registerPassword.text.toString().trim()
            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(context, "enter the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                FireStore.addUserToSystem(name, email, password){
                    //TODO: update the ui
                    if(it)
                        Toast.makeText(context, "successfully signed up", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(context, "couldn't signed up", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}