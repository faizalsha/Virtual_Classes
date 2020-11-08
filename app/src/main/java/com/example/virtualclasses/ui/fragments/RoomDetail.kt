package com.example.virtualclasses.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.virtualclasses.R
import com.example.virtualclasses.utils.Communicator
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_room_detail.*


class RoomDetail : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val room = Communicator.room!!
        tvRoomName.text = room.roomTitle
        tvRoomDescription.text = room.roomDescription
        tvRoomOwnerId.text = room.ownerId
        tvRoomId.text = room.roomId
        btnShareInfo.setOnClickListener {
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
//            whatsappIntent.setPackage("com.whatsapp")
//            val message = "${room.roomTitle}/${room.roomDescription}/${room.ownerId}/${room.roomId}"
            val message = Gson().toJson(room)
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, message)
            try {
                requireActivity().startActivity(whatsappIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}