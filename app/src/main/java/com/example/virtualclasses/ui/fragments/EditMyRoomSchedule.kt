package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.DaySchedule
import com.example.virtualclasses.model.DefaultDaySchedule
import com.example.virtualclasses.model.WeekDay
import com.example.virtualclasses.ui.adapter.PerDayScheduleAdapter
import com.example.virtualclasses.utils.Utility
import io.grpc.okhttp.internal.Util
import kotlinx.android.synthetic.main.fragment_edit_my_room_schedule.*
import kotlinx.android.synthetic.main.fragment_view_subscribed_room_schedule.*
import kotlinx.android.synthetic.main.fragment_view_subscribed_room_schedule.daySpinner
import kotlinx.android.synthetic.main.item_day.*
import java.util.*

class EditMyRoomSchedule : Fragment() {
    val args: EditMyRoomScheduleArgs by navArgs()
    private var defaultIndex = 0
    private var dayIndex = 1
    lateinit var perDayScheduleAdapter: PerDayScheduleAdapter
    private lateinit var daySchedule: DaySchedule
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_my_room_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daySchedule = DefaultDaySchedule(mutableListOf(), args.room.roomId, args.room.ownerId, WeekDay.SUNDAY)
        val dayOfWeekIndex = args.weekOfDayIndex
        val defaultOrUpdated = args.defaultOrUpdated
        setupRecyclerview()
        setupSpinner(dayOfWeekIndex, defaultOrUpdated)
        getDataFromFirebase()
        setupFAB()
    }
    private fun setupRecyclerview(){
        if(context == null) return
        perDayScheduleAdapter = PerDayScheduleAdapter(daySchedule, requireContext(), parentFragmentManager)
        scheduleRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = perDayScheduleAdapter
        }
    }
    private fun setupSpinner(dayOfWeek: Int, defaultOrUpdated: Int) {
        dayIndex = dayOfWeek
        defaultIndex = 0
        if(context != null) {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.daysOfWeek,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                daySpinner.adapter = adapter
            }
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.default_updated,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                default_updated.adapter = adapter
            }
        }
        daySpinner.setSelection(dayOfWeek)
        default_updated.setSelection(defaultOrUpdated)
        setSpinnerCallbacks()
    }




    private fun setSpinnerCallbacks(){
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                Toast.makeText(context, "position: $pos selected", Toast.LENGTH_SHORT).show()
                dayIndex = pos
                getDataFromFirebase()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
            }

        }
        default_updated.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                Toast.makeText(context, "position: $pos selected", Toast.LENGTH_SHORT).show()
                defaultIndex = pos
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDataFromFirebase(){
        FireStore.getDefaultDaySchedule(dayIndex, args.room.roomId){
            if(it == null){
                Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                return@getDefaultDaySchedule
            }
            daySchedule.schedules = it.schedules
            perDayScheduleAdapter.notifyDataSetChanged()
            Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFAB(){
        fab.setOnClickListener {
            val action =
                EditMyRoomScheduleDirections.actionEditMyRoomScheduleToScheduleFormFragment(daySchedule)
            findNavController().navigate(action)
        }
    }
}