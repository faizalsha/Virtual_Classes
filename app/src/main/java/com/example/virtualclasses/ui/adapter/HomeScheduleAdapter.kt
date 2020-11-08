package com.example.virtualclasses.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualclasses.R
import com.example.virtualclasses.model.DaySchedule
import com.example.virtualclasses.model.Schedule
import kotlinx.android.synthetic.main.dialog_schedule_fragment.view.*
import kotlinx.android.synthetic.main.item_schedule.view.*

class HomeScheduleAdapter(private val data: DaySchedule, val context: Context, val listener: () -> Unit): RecyclerView.Adapter<HomeScheduleAdapter.HomeScheduleHolder>() {
    class HomeScheduleHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(schedule: Schedule, listener: () -> Unit) {
            itemView.startTimeTextView.text =
                "${schedule.startTime.hour}:${schedule.startTime.minute} ${schedule.startTime.midday}"
            itemView.endTimeTextView.text =
                "${schedule.endTime.hour}:${schedule.endTime.minute} ${schedule.endTime.midday}"
            itemView.scheduleTitle.text = schedule.title
            itemView.scheduleDescription.text = schedule.description
            itemView.setOnClickListener {
                listener()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeScheduleHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false))

    override fun onBindViewHolder(holder: HomeScheduleHolder, position: Int) {
        holder.bind(data.schedules[position], listener)
    }

    override fun getItemCount() = data.schedules.size

}