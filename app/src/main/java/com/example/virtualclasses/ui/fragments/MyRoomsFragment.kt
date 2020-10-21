package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireAuth
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.Room
import com.example.virtualclasses.ui.adapter.RoomAdapter
import kotlinx.android.synthetic.main.fragment_my_rooms.*

class MyRoomsFragment : Fragment() {
    lateinit var roomAdapter: RoomAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_rooms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //if current user is null send user to login screen to login again
        FireStore.getMyAllRooms(FireAuth.getCurrentUser()!!.uid){
            if(it == null){
                Toast.makeText(context, "No rooms found", Toast.LENGTH_LONG).show()
                return@getMyAllRooms
            }else{
                setupRecyclerView(it)
            }
        }
        addRoom.setOnClickListener {
            val action = MyRoomsFragmentDirections.actionMyRoomsFragmentToAddRoomFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView(it: MutableList<Room>) {
        roomAdapter = RoomAdapter(it, requireContext())
        roomRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = roomAdapter
        }
    }

}