package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.RoomInfo
import kotlinx.android.synthetic.main.fragment_subscribe_new_room.*

class SubscribeNewRoomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribe_new_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }
    private fun setupListener(){
        subscribeNewRoomButton.setOnClickListener {
            val ownerId = roomOwnerId.text.toString()
            val roomId = roomId.text.toString()
            val roomInfo = RoomInfo("","",ownerId, roomId)
            //todo: first check ownerId != currentUser.uid
            FireStore.subscribe(roomInfo){
                if(!it){
                    Toast.makeText(context, "check your credential", Toast.LENGTH_LONG).show()
                    return@subscribe
                }
                Toast.makeText(context, "subscribed successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}