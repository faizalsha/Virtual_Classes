package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.virtualclasses.R
import com.example.virtualclasses.local.Constants
import com.example.virtualclasses.local.SharedPrefsUtils
import com.example.virtualclasses.model.Room
import com.example.virtualclasses.model.RoomInfo
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val room = SharedPrefsUtils.getObject(Constants.HOME_ROOM, context, RoomInfo::class.java)
            Toast.makeText(context, "${room.roomTitle} selected", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(context, "exception: $e", Toast.LENGTH_LONG).show()
        }
        setupListener()
    }

    private fun setupListener(){
//        subscribedRoom.setOnClickListener {
//            val action = HomeFragmentDirections.actionHomeFragmentToSubscribedRoomFragment()
//            findNavController().navigate(action)
//        }
//        myRoom.setOnClickListener {
//            val action = HomeFragmentDirections.actionHomeFragmentToMyRoomsFragment()
//            findNavController().navigate(action)
//        }
        tvSelectRoom.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSelectRoomFragment())
        }
        subscribedRoomsView.setOnClickListener {
            SubscribedRoomBottomSheetFragment().show(parentFragmentManager, "subscribed room bottom sheet")
        }
        myRoomsView.setOnClickListener {
            MyRoomBottomSheetFragment().show(parentFragmentManager, "my room bottom sheet")
        }
    }
}