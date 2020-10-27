package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.virtualclasses.R
import com.example.virtualclasses.utils.Utility
import io.grpc.okhttp.internal.Util
import kotlinx.android.synthetic.main.fragment_view_subscribed_room_schedule.*
import kotlinx.android.synthetic.main.item_day.*
import java.util.*

class EditMyRoomSchedule : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_my_room_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner(Utility.getCurrentDayOfWeekIndex() - 1)
    }

    private fun setupSpinner(dayOfWeek: Int) {
        if(context != null) {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.daysOfWeek,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                daySpinner.adapter = adapter
            }
        }
        daySpinner.setSelection(dayOfWeek)
        daySpinner.onItemSelectedListener = this
    }
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Toast.makeText(context, "position: $pos selected", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
        Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
    }
}