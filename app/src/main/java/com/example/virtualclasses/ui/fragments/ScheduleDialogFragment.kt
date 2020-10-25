package com.example.virtualclasses.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.virtualclasses.R
import com.example.virtualclasses.model.Schedule
import com.example.virtualclasses.model.ScheduleTime
import com.example.virtualclasses.utils.Utility
import kotlinx.android.synthetic.main.dialog_schedule_fragment.view.*
import kotlinx.android.synthetic.main.item_schedule.view.*

class ScheduleDialogFragment(val listener: (Schedule) -> Unit): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val dialogView = layoutInflater.inflate(R.layout.dialog_schedule_fragment, null)
            builder.setView(dialogView)
            var startTime = ScheduleTime()
            var endTime = ScheduleTime()
            dialogView.startTime.setOnClickListener{
                if(context == null) return@setOnClickListener
                Utility.selectTime(requireContext()){ scheduleTime ->
                    startTime = scheduleTime
                    dialogView.selectedStartTime.text = "${startTime.hour}:${startTime.minute} ${startTime.midday}"
                }
            }
            dialogView.endTime.setOnClickListener {
                if(context == null) return@setOnClickListener
                Utility.selectTime(requireContext()){ scheduleTime ->
                    endTime = scheduleTime
                    dialogView.selectedEndTime.text = "${endTime.hour}:${endTime.minute} ${endTime.midday}"
                }
            }
            dialogView.saveSchedule.setOnClickListener {

                val title = dialogView.scheduleTitleEditText.text.toString()
                val description = dialogView.scheduleDescriptionEditText.text.toString()
                if(title.trim().isEmpty()) return@setOnClickListener
                val schedule = Schedule(title, description, "", "", startTime, endTime)
                listener(schedule)
                dialog?.dismiss()
            }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}