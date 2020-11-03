package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.DefaultDaySchedule
import com.example.virtualclasses.ui.adapter.ViewSubscribedRoomScheduleAdapter
import com.example.virtualclasses.utils.Communicator
import com.example.virtualclasses.utils.Utility
import kotlinx.android.synthetic.main.fragment_view_subscribed_room_schedule.*

class ViewSubscribedRoomSchedule : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var viewSubscribedRoomScheduleAdapter: ViewSubscribedRoomScheduleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_subscribed_room_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Communicator.daySchedule = DefaultDaySchedule(
            mutableListOf(),
            Communicator.room!!.roomId,
            Communicator.room!!.ownerId,
            Utility.indexToWeekDay[Communicator.dayOfWeekIndex]!!
        )
        setupRecyclerView()
        setSpinner()
    }

    private fun setupRecyclerView() {
        if (context == null)
            return
        viewSubscribedRoomScheduleAdapter = ViewSubscribedRoomScheduleAdapter(
            Communicator.daySchedule!!,
            requireContext(),
            parentFragmentManager
        )
        viewSubscribedRoomRecyclerView.apply {
            adapter = viewSubscribedRoomScheduleAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setSpinner() {
        if (context != null) {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.daysOfWeek,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                daySpinner.adapter = adapter

            }
        }
        daySpinner.setSelection(Communicator.dayOfWeekIndex!!)
        daySpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Toast.makeText(context, "${parent.getItemAtPosition(pos)} selected", Toast.LENGTH_SHORT)
            .show()
        Communicator.dayOfWeekIndex = pos
        Communicator.date = Utility.getCurrentOrNextWeekDayDate(
            Utility.getCurrentCalendar(),
            Utility.indexToWeekDay[Communicator.dayOfWeekIndex]!!
        )
        Communicator.daySchedule!!.schedules.clear()
        getSchedule()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
    }

    private fun getSchedule() {
        FireStore.getSubscribedRoomUpdatedOrDefaultDaySchedule(
            Communicator.date!!,
            Communicator.dayOfWeekIndex!!,
            Communicator.room!!.ownerId,
            Communicator.room!!.roomId
        ) { daySchedule ->
            if (daySchedule == null) {
                Toast.makeText(context, "No Schedule Found", Toast.LENGTH_LONG).show()
            } else {
                Communicator.daySchedule!!.schedules = daySchedule.schedules
                viewSubscribedRoomScheduleAdapter.notifyDataSetChanged()
            }
        }
    }

}