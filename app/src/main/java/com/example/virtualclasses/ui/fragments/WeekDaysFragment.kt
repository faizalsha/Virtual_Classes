package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.ui.adapter.WeekDaysAdapter
import kotlinx.android.synthetic.main.fragment_week_days.*
import java.util.*
import kotlin.collections.ArrayList

class WeekDaysFragment : Fragment() {

    lateinit var weekDaysAdapter: WeekDaysAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_days, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val weekDaysArray =
            resources.getStringArray(R.array.daysOfWeek).toCollection(ArrayList())
        weekDaysAdapter = WeekDaysAdapter(weekDaysArray, requireContext())
        weekdaysRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weekDaysAdapter
        }
    }
}