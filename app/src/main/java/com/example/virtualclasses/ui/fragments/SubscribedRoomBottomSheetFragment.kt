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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_my_room_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_subscribed_room_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_subscribed_room_bottom_sheet.backButton

class SubscribedRoomBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var roomsAdapter: RoomsAdapter
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
        roomsAdapter = RoomsAdapter(rooms, requireContext()){
            val action =
                HomeFragmentDirections.actionHomeFragmentToViewSubscribedRoomSchedule()
            findNavController().navigate(action)
            dismiss()

        }
        subscribedRoomBottomRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = roomsAdapter
        }
    }
    private fun setupListener(){
        backButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setFirebase(){
        if(FireAuth.getCurrentUser() == null){
            //logout user
            setUIVisibility(false)
            return
        }
        FireStore.getSubscribedRooms(FireAuth.getCurrentUser()!!.uid){
            if(it == null){
                setUIVisibility(false)
                Toast.makeText(context, "no updates", Toast.LENGTH_SHORT).show()
                return@getSubscribedRooms
            }
            rooms.clear()
            it.forEach { room ->
                rooms.add(room)
            }
            roomsAdapter.notifyDataSetChanged()
            setUIVisibility(false)
        }
    }

    private fun setUIVisibility(makeVisible: Boolean){
        if(!makeVisible)
            subscribedRoomBottomProgressBar.visibility = View.GONE
        else
            subscribedRoomBottomProgressBar.visibility = View.VISIBLE
    }
}