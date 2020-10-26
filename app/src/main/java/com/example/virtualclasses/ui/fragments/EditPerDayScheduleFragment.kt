package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.model.DefaultDaySchedule
import com.example.virtualclasses.model.Schedule
import com.example.virtualclasses.ui.adapter.PerDayScheduleAdapter
import com.example.virtualclasses.utils.Utility
import com.google.type.Date
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_edit_per_day_schedule.*

const val TAG = "EditPerDaySchedule"
class EditPerDayScheduleFragment : Fragment() {
    private val args: EditPerDayScheduleFragmentArgs by navArgs()
    lateinit var perDayScheduleAdapter: PerDayScheduleAdapter
    private val defaultDaySchedule = DefaultDaySchedule()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_per_day_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()
        setupFirebase()
    }

    private fun setupRecyclerView(){
        if(context == null) return
        perDayScheduleAdapter = PerDayScheduleAdapter(defaultDaySchedule, requireContext(), parentFragmentManager)
        defaultRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = perDayScheduleAdapter
            isNestedScrollingEnabled = false
        }
    }
    private fun setupListener(){
        addSchedule.setOnClickListener {
            if(context == null) return@setOnClickListener
            ScheduleDialogFragment{
                Log.d(TAG, "onViewCreated: $it")
                updateSchedule(it)
            }.show(parentFragmentManager, "schedule fragment")
        }

        saveDefaultSchedule.setOnClickListener {
            FireStore.saveDefaultDaySchedule(args.dayOfWeekIndex, defaultDaySchedule, args.room.roomId){
                if(it)
                    Toast.makeText(context, "successfully saved day schedule", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupFirebase(){
        FireStore.getDefaultDaySchedule(args.dayOfWeekIndex, args.room.roomId){
            if(it != null){
                Toast.makeText(context, "successfully received schedule from firebase ${it.schedules.size}", Toast.LENGTH_LONG).show()
                updateDefaultDaySchedule(it)
            }
            else
                Toast.makeText(context, "couldn't get schedule from firebase", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateSchedule(schedule: Schedule){
        this.defaultDaySchedule.schedules.add(schedule)
        perDayScheduleAdapter.notifyDataSetChanged()
    }
    private fun updateDefaultDaySchedule(defaultDaySchedule: DefaultDaySchedule){
        this.defaultDaySchedule.updateWith(defaultDaySchedule)
        perDayScheduleAdapter.notifyDataSetChanged()
    }
}