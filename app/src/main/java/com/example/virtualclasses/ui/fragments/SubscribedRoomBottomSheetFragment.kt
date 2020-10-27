package com.example.virtualclasses.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
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
import com.example.virtualclasses.model.RoomInfo
import com.example.virtualclasses.ui.adapter.MyAdapter
import com.example.virtualclasses.ui.adapter.SubscribedRoomsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_subscribed_room.*
import kotlinx.android.synthetic.main.fragment_subscribed_room_bottom_sheet.*

class SubscribedRoomBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var myAdapter: MyAdapter
    private val rooms: ArrayList<Room> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribed_room_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListener()
        setFirebase()
    }

    private fun setupRecyclerView() {
        if(context == null) return
        myAdapter = MyAdapter(rooms, requireContext()){

        }
        subscribedRoomBottomRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myAdapter
        }
    }
    private fun setupListener(){
        backButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setFirebase(){
        FireStore.getSubscribedRooms(FireAuth.getCurrentUser()!!.uid){
            if(it == null){
                subscribedRoomBottomProgressBar.visibility = View.GONE
                Toast.makeText(context, "no updates", Toast.LENGTH_SHORT).show()
                return@getSubscribedRooms
            }
            rooms.clear()
            it.forEach { room ->
                rooms.add(room)
            }
            myAdapter.notifyDataSetChanged()
            subscribedRoomBottomProgressBar.visibility = View.GONE
        }
    }
}