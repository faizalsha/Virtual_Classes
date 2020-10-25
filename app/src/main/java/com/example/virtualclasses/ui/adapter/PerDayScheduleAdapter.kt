package com.example.virtualclasses.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualclasses.R
import com.example.virtualclasses.model.DefaultDaySchedule
import com.example.virtualclasses.model.Schedule
import kotlinx.android.synthetic.main.item_schedule.view.*

class PerDayScheduleAdapter(val defaultDaySchedule: DefaultDaySchedule, val context: Context): RecyclerView.Adapter<PerDayScheduleAdapter.PerDayScheduleHolder>() {
    class PerDayScheduleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(schedule: Schedule, position: Int){
            itemView.startTimeTextView.text =
                "${schedule.startTime.hour}:${schedule.startTime.minute} ${schedule.startTime.midday}"
            itemView.endTimeTextView.text =
                "${schedule.endTime.hour}:${schedule.endTime.minute} ${schedule.endTime.midday}"
            itemView.scheduleTitle.text = schedule.title
            itemView.scheduleDescription.text = schedule.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PerDayScheduleHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_schedule, parent, false)
        )

    override fun onBindViewHolder(holder: PerDayScheduleHolder, position: Int) {
        holder.bind(defaultDaySchedule.schedules[position], position)
    }

    override fun getItemCount() = defaultDaySchedule.schedules.size

    fun update(schedule: Schedule){
        defaultDaySchedule.schedules.add(schedule)
        notifyDataSetChanged()
    }
}