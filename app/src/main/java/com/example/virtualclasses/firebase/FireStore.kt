package com.example.virtualclasses.firebase

import com.example.virtualclasses.local.Constants
import com.example.virtualclasses.model.Student
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore

object FireStore {

    private val mFireStoreRef: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun addUserToSystem(name: String, email: String , password: String, listener: (Boolean)->Unit){
        FireAuth.registerUser(email, password){ result: Any,  successful: Boolean ->
            //save user in firestore
            if(successful && result is AuthResult){
                val student = Student(name, email, null, null, false)
                saveUserDetailsToFireStore(student, result.user!!.uid)
                listener(true)
            }else{
                //show toast
                listener(false)
            }
        }
    }
    private fun saveUserDetailsToFireStore(student: Student, uid: String){
        mFireStoreRef.collection(Constants.USERS).document(uid)
            .set(student)
    }

    fun checkUserExistInFireStore(uid: String, listener: (Boolean) -> Unit){
        mFireStoreRef.collection(Constants.USERS).document(uid).get().addOnSuccessListener {
            if (it.exists()){
                listener(true)
            }else{
                listener(false)
            }
        }.addOnFailureListener {
            listener(false)
        }
    }
}