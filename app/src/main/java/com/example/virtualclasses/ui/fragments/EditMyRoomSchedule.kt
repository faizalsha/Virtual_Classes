package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.DefaultDaySchedule
import com.example.virtualclasses.ui.adapter.PerDayScheduleAdapter
import com.example.virtualclasses.utils.Communicator
import com.example.virtualclasses.utils.Utility
import kotlinx.android.synthetic.main.fragment_edit_my_room_schedule.*
import kotlinx.android.synthetic.main.fragment_view_subscribed_room_schedule.daySpinner
import java.util.*

class EditMyRoomSchedule : Fragment() {
    lateinit var perDayScheduleAdapter: PerDayScheduleAdapter
    var isLoading = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_my_room_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Communicator.daySchedule = DefaultDaySchedule(mutableListOf(), Communicator.room!!.roomId, Communicator.room!!.ownerId, Utility.indexToWeekDay[Communicator.dayOfWeekIndex]!!)
        setupRecyclerview()
        setupSpinner(Communicator.dayOfWeekIndex!!, Communicator.default_updated!!)
        setupFAB()
    }
    private fun setupRecyclerview(){
        if(context == null) return
        perDayScheduleAdapter = PerDayScheduleAdapter(Communicator.daySchedule!!, requireContext(), parentFragmentManager)
        scheduleRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = perDayScheduleAdapter
        }
    }
    private fun setupSpinner(dayOfWeek: Int, defaultOrUpdated: Int) {
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
                Communicator.dayOfWeekIndex = pos
                Communicator.date = Utility.getNextWeekDay(
                    Utility.getCurrentDate(),
                    Utility.indexToWeekDay[Communicator.dayOfWeekIndex]!!
                )
                //check what to show default or updated
                showCorrectSchedule()
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
                Communicator.default_updated = pos
                showCorrectSchedule()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDefaultDaySchedule(){
        FireStore.getDefaultDaySchedule(Communicator.dayOfWeekIndex!!, Communicator.room!!.roomId){
            isLoading = false
            if(it == null){
                Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                return@getDefaultDaySchedule
            }
            Communicator.daySchedule!!.schedules = it.schedules
            perDayScheduleAdapter.notifyDataSetChanged()
            Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getUpdatedDaySchedule(date: Date){
        FireStore.getUpdatedDaySchedule(date, Communicator.dayOfWeekIndex!!, Communicator.room!!.roomId){
            isLoading = false
            if(it == null){
                Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                return@getUpdatedDaySchedule
            }
            Communicator.daySchedule!!.schedules = it.schedules
            perDayScheduleAdapter.notifyDataSetChanged()
            Toast.makeText(context, "updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFAB() {
        fab.setOnClickListener {
            val action =
                EditMyRoomScheduleDirections.actionEditMyRoomScheduleToScheduleFormFragment()
            findNavController().navigate(action)
        }
    }

    private fun showCorrectSchedule() {
        if (!isLoading) {
            if (Communicator.default_updated == 0) {
                //update is selected
                val dayIndex = Communicator.dayOfWeekIndex!!
                val weekDay = Utility.indexToWeekDay[dayIndex]
                val now = Utility.getCurrentDate()
                val date = Utility.getNextWeekDay(now, weekDay!!)
                getUpdatedDaySchedule(date)
            } else {
                //default is selected
                getDefaultDaySchedule()
            }
            isLoading = true
        }
    }
}