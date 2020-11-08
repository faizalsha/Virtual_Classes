package com.example.virtualclasses.firebase

import android.content.Context
import android.widget.Toast
import com.example.virtualclasses.model.Student
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object FireAuth{

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }


    fun registerUser(email: String, password: String, listener: (Any, Boolean) -> Unit){
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            listener(it, true)
        }.addOnFailureListener {
            listener(it, false)
        }
    }

    fun logout(listener: () -> Unit){
        mAuth.signOut()
        listener()
    }

    fun loginStudent(email: String, password: String, listener: (Any, Boolean) -> Unit){
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            listener(it, true)
        }.addOnFailureListener {
            listener(it, false)
        }
    }

    fun getCurrentUser() =
        mAuth.currentUser

}