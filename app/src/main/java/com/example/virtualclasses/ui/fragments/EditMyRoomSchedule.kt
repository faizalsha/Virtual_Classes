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
import io.grpc.okhttp.internal.Util
import kotlinx.android.synthetic.main.fragment_edit_my_room_schedule.*
import kotlinx.android.synthetic.main.fragment_view_subscribed_room_schedule.daySpinner
import java.util.*
import kotlin.collections.ArrayList

class EditMyRoomSchedule : Fragment() {
    lateinit var perDayScheduleAdapter: PerDayScheduleAdapter
    var isLoading = false
    private lateinit var arraySpinner: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
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
            arraySpinner = ArrayList(resources.getStringArray(R.array.daysOfWeek).asList())
            adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, arraySpinner)
            daySpinner.adapter = adapter

            val arrayDefaultSpinner = resources.getStringArray(R.array.default_updated).asList()
            val defaultUpdatedAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayDefaultSpinner)
            default_updated.adapter = defaultUpdatedAdapter
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
                if(pos == 7){
                    if(context == null) return
                    Utility.selectDate(requireContext()){
                        arraySpinner[7] = "${it.date}/${it.month}/${it.year}"
                        adapter.notifyDataSetChanged()
                        Communicator.date = it
                        Communicator.dayOfWeekIndex = it.day
                        Communicator.daySchedule!!.schedules.clear()
                        showCorrectSchedule()
                    }
                }else{
                    arraySpinner[7] = "select a date"
                    Communicator.dayOfWeekIndex = pos
                    Communicator.date = Utility.getCurrentOrNextWeekDayDate(
                        Utility.getCurrentCalendar(),
                        Utility.indexToWeekDay[Communicator.dayOfWeekIndex]!!
                    )
                    Communicator.daySchedule!!.schedules.clear()
                    showCorrectSchedule()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }

        }
        default_updated.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                Communicator.default_updated = pos
                showCorrectSchedule()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
    }

    private fun getDefaultDaySchedule(){
        Communicator.isFirebaseLoading = true
        FireStore.getDefaultDaySchedule(Communicator.dayOfWeekIndex!!, Communicator.room!!.roomId){
            isLoading = false
            if(it == null){
                Communicator.isFirebaseLoading = false
                return@getDefaultDaySchedule
            }
            Communicator.daySchedule!!.schedules = it.schedules
            perDayScheduleAdapter.notifyDataSetChanged()
            Communicator.isFirebaseLoading = false
        }
    }
    private fun getUpdatedOrDefaultSchedule(date: Date){
        Communicator.isFirebaseLoading = true
        FireStore.getUpdatedOrDefaultDaySchedule(date, Communicator.dayOfWeekIndex!!, Communicator.room!!.roomId){
            isLoading = false
            if(it == null){
                Communicator.isFirebaseLoading = false
                return@getUpdatedOrDefaultDaySchedule
            }
            Communicator.daySchedule!!.schedules = it.schedules
            perDayScheduleAdapter.notifyDataSetChanged()
            Communicator.isFirebaseLoading = false
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
            //clear adapter data
            Communicator.daySchedule!!.schedules.clear()
            if (Communicator.default_updated == 0) {
                //update is selected
                val dayIndex = Communicator.dayOfWeekIndex!!
                val weekDay = Utility.indexToWeekDay[dayIndex]
                val now = Utility.getCurrentCalendar()
                val date = Utility.getCurrentOrNextWeekDayDate(now, weekDay!!)
//                getUpdatedDaySchedule(date)
                getUpdatedOrDefaultSchedule(date)
            } else {
                //default is selected
                getDefaultDaySchedule()
            }
            isLoading = true
        }
    }
}