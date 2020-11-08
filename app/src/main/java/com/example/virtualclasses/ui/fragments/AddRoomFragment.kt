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
import com.example.virtualclasses.model.RoomDetails
import com.example.virtualclasses.utils.Communicator
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

            createRoom.visibility = View.GONE
            pbCreateRoom.visibility = View.VISIBLE
            val room = RoomDetails("",
                title,
                desc,
                FireAuth.getCurrentUser()!!.uid,
                FireAuth.getCurrentUser()!!.email?: "default email",
                1,
                Calendar.getInstance().time.toString())
            Communicator.isFirebaseLoading = true
            FireStore.createRoom(room, FireAuth.getCurrentUser()!!.uid){
                if(it){
                    Toast.makeText(context, "room created Successfully", Toast.LENGTH_LONG).show()
                    Communicator.isFirebaseLoading = false
                    activity?.onBackPressed()
                }else{
                    Toast.makeText(context, "some error occurred", Toast.LENGTH_LONG).show()
                }
                Communicator.isFirebaseLoading = false

                createRoom.visibility = View.VISIBLE
                pbCreateRoom.visibility = View.GONE
            }
        }
    }
}