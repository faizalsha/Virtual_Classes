package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireAuth
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.local.Constants
import com.example.virtualclasses.local.SharedPrefsUtils
import com.example.virtualclasses.model.RoomInfo
import com.example.virtualclasses.ui.adapter.HomeScheduleAdapter
import com.example.virtualclasses.utils.Utility
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private lateinit var homeScheduleAdapter: HomeScheduleAdapter
    private lateinit var roomInfo: RoomInfo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        try {
            roomInfo =
                SharedPrefsUtils.getObject(Constants.HOME_ROOM, context, RoomInfo::class.java)
            Toast.makeText(context, "${roomInfo.roomTitle} selected", Toast.LENGTH_LONG).show()
            val calendar = Utility.getCurrentCalendar()
            FireStore.getSubscribedRoomUpdatedOrDefaultDaySchedule(
                calendar.time,
                Utility.getCurrentDayOfWeekIndex(),
                roomInfo.ownerId,
                roomInfo.roomId
            ) {
                if (it == null) {
                    Toast.makeText(context, "No Schedule found", Toast.LENGTH_LONG).show()
                } else {
                    if (context == null) return@getSubscribedRoomUpdatedOrDefaultDaySchedule
                    homeScheduleAdapter = HomeScheduleAdapter(it, requireContext()) {
                        Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show()
                    }
                    rvHomeFragment.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = homeScheduleAdapter
                    }
                    Toast.makeText(context, "recycler view is set", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "exception: $e", Toast.LENGTH_LONG).show()
        }
        setupListener()
    }

    private fun setupListener(){
        tvSelectRoom.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSelectRoomFragment())
        }
        subscribedRoomsView.setOnClickListener {
            SubscribedRoomBottomSheetFragment().show(
                parentFragmentManager,
                "subscribed room bottom sheet"
            )
        }
        myRoomsView.setOnClickListener {
            MyRoomBottomSheetFragment().show(parentFragmentManager, "my room bottom sheet")
        }
    }
}