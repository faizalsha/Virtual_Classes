package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireAuth
import com.example.virtualclasses.firebase.FireStore
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }


    private fun setupListener(){
        buttonSignIn.setOnClickListener {
            val email = editTextTextEmail.text.trim().toString()
            val password = editTextPassword.text.trim().toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(context, "Enter Credential", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                FireAuth.loginStudent(email, password){ any: Any, b: Boolean ->
                    if(any is Exception){
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                        val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }
        textViewRegister.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }
}