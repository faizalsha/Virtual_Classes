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
import java.util.*
import kotlin.collections.ArrayList

class ViewSubscribedRoomSchedule : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var viewSubscribedRoomScheduleAdapter: ViewSubscribedRoomScheduleAdapter
    private lateinit var arraySpinner: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
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
        arraySpinner = ArrayList<String>(resources.getStringArray(R.array.daysOfWeek).toList())
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
            adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, arraySpinner)
            daySpinner.adapter = adapter
        }
        daySpinner.setSelection(Communicator.dayOfWeekIndex!!)
        daySpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        if(pos == 7){
            if(context == null) return
            Utility.selectDate(requireContext()){
                arraySpinner[7] = "${it.date}/${it.month}/${it.year}"
                adapter.notifyDataSetChanged()
                Communicator.date = it
                Communicator.dayOfWeekIndex = it.day
                Communicator.daySchedule!!.schedules.clear()
                getSchedule()
            }
        }else{
            arraySpinner[7] = "select a date"
            Communicator.dayOfWeekIndex = pos
            Communicator.date = Utility.getCurrentOrNextWeekDayDate(
                Utility.getCurrentCalendar(),
                Utility.indexToWeekDay[Communicator.dayOfWeekIndex]!!
            )
            Communicator.daySchedule!!.schedules.clear()
            getSchedule()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
    }

    private fun getSchedule() {
        Communicator.isFirebaseLoading = true
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
            Communicator.isFirebaseLoading = false
        }
    }

}