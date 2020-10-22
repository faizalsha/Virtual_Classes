package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireAuth
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.RoomInfo
import com.example.virtualclasses.ui.adapter.SubscribedRoomsAdapter
import kotlinx.android.synthetic.main.fragment_subscribed_room.*

class SubscribedRoomsFragment : Fragment() {
    lateinit var subscribedRoomsAdapter: SubscribedRoomsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribed_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeRoomButton.setOnClickListener {
            val action = SubscribedRoomsFragmentDirections
                .actionSubscribedRoomFragmentToSubscribeNewRoomFragment()
            findNavController().navigate(action)
        }
        FireStore.getSubscribedRooms(FireAuth.getCurrentUser()!!.uid){
            setupRecyclerView(it)
        }
    }

    private fun setupRecyclerView(data: List<RoomInfo>?) {
        if(data == null) return
        subscribedRoomsAdapter = SubscribedRoomsAdapter(data, requireContext())
        subscribedRoom.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = subscribedRoomsAdapter
        }
    }
}