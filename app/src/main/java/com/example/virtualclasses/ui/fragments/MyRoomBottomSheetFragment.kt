package com.example.virtualclasses.ui.fragments

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
import com.example.virtualclasses.ui.adapter.RoomsAdapter
import com.example.virtualclasses.utils.Communicator
import com.example.virtualclasses.utils.Utility
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_my_room_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_my_room_bottom_sheet.backButton

class MyRoomBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var roomsRoomAdapter: RoomsAdapter
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
        roomsRoomAdapter = RoomsAdapter(rooms, requireContext(), listener = {
            /*sending data to communicator*/
            Communicator.room = it
            Communicator.dayOfWeekIndex = Utility.getCurrentDayOfWeekIndex()
            Communicator.default_updated = 0
            /*end*/
            val action =
                HomeFragmentDirections.actionHomeFragmentToEditMyRoomSchedule()
            findNavController().navigate(action)
            dismiss()
        }, detail = {
            Communicator.room = it
            findNavController().navigate(R.id.action_global_roomDetail)
            dismiss()
        })
        myRoomBottomRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = roomsRoomAdapter
        }
    }
    private fun setupFirebase(){
        if(FireAuth.getCurrentUser() == null){
            //log out the user
            setUIVisibility(false)
            return
        }
        Communicator.isFirebaseLoading = true
        FireStore.getMyAllRooms(FireAuth.getCurrentUser()!!.uid){
            if(it == null){
                setUIVisibility(false)
                Communicator.isFirebaseLoading = false
                return@getMyAllRooms
            }
            rooms.clear()
            it.forEach { roomDetails ->
                rooms.add(roomDetails)
            }
            roomsRoomAdapter.notifyDataSetChanged()
            setUIVisibility(false)
            Communicator.isFirebaseLoading = false
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
        btnCreateRoom.setOnClickListener {
            findNavController().navigate(R.id.action_global_addRoomFragment)
            dismiss()
        }
    }
}