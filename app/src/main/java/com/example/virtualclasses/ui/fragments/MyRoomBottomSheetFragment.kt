package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireAuth
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.Room
import com.example.virtualclasses.ui.adapter.MyAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_my_room_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_my_room_bottom_sheet.backButton
import kotlinx.android.synthetic.main.fragment_subscribed_room_bottom_sheet.*

class MyRoomBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var myRoomAdapter: MyAdapter
    private val rooms: ArrayList<Room> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_room_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFirebase()
        setupListener()
    }
    private fun setupRecyclerView(){
        if(context == null) return
        myRoomAdapter = MyAdapter(rooms, requireContext()){
            //todo: send to different fragment
        }
        myRoomBottomRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myRoomAdapter
        }
    }
    private fun setupFirebase(){
        if(FireAuth.getCurrentUser() == null){
            //log out the user
            setUIVisibility(false)
            return
        }
        FireStore.getMyAllRooms(FireAuth.getCurrentUser()!!.uid){
            if(it == null){
                setUIVisibility(false)
                return@getMyAllRooms
            }
            rooms.clear()
            it.forEach { roomDetails ->
                rooms.add(roomDetails)
            }
            myRoomAdapter.notifyDataSetChanged()
            setUIVisibility(false)
        }
    }
    private fun setUIVisibility(makeVisible: Boolean){
        if(!makeVisible)
            myRoomBottomProgressBar.visibility = View.GONE
        else
            myRoomBottomProgressBar.visibility = View.VISIBLE
    }
    private fun setupListener(){
        backButton.setOnClickListener {
            dismiss()
        }
    }
}