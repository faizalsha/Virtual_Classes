package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireAuth
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.Room
import kotlinx.android.synthetic.main.fragment_add_room.*
import java.util.*

class AddRoomFragment : Fragment() {
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }
    private fun setupListener(){
        createRoom.setOnClickListener {
            val title = roomTitle.text.toString().trim()
            val desc = roomDescription.text.toString().trim()
            if(title.isEmpty() || desc.isEmpty()) return@setOnClickListener
            //if current user is null log out and send to login screen
            val room = Room("",
                title,
                desc,
                FireAuth.getCurrentUser()!!.uid,
                FireAuth.getCurrentUser()!!.uid,
                FireAuth.getCurrentUser()!!.email?: "default email",
                0,
                Calendar.getInstance().time.toString())
            FireStore.createRoom(room, FireAuth.getCurrentUser()!!.uid){
                if(it){
                    Toast.makeText(context, "room created Successfull", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "some error occured", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}