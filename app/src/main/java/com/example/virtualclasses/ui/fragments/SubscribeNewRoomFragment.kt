package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.Room
import com.example.virtualclasses.model.RoomInfo
import com.example.virtualclasses.utils.Communicator
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_subscribe_new_room.*
import java.lang.Exception

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
            val code = etRoomCode.text.toString().trim()
            if(code.isEmpty()) return@setOnClickListener
//            val cred = code.split("/")
//            if(cred.size != 4){
//                Toast.makeText(context, "Invalid Code", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            val roomTitle = cred[0]
//            val roomDesc = cred[1]
//            val ownerId = cred[2]
//            val roomId = cred[3]
//            val roomInfo = RoomInfo(roomId,roomTitle,roomDesc, ownerId)
            var roomInfo = RoomInfo()
            try {
                roomInfo = Gson().fromJson(code, RoomInfo::class.java)
            }catch (e: Exception){
                return@setOnClickListener
            }

            //todo: first check ownerId != currentUser.uid
            Communicator.isFirebaseLoading = true
            FireStore.subscribe(roomInfo, listener = {
                if (!it) {
                    Toast.makeText(context, "check your credential", Toast.LENGTH_LONG).show()
                    Communicator.isFirebaseLoading = false
                    return@subscribe
                }
                Toast.makeText(context, "subscribed successfully", Toast.LENGTH_SHORT).show()
                Communicator.isFirebaseLoading = false
            }, alreadySubscribed = {
                if (it) {
                    Toast.makeText(context, "already subscribed", Toast.LENGTH_SHORT).show()
                    Communicator.isFirebaseLoading = false
                }
            })
        }
    }
}