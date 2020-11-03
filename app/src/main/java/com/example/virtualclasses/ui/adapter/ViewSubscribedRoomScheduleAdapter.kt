package com.example.virtualclasses.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualclasses.R
import com.example.virtualclasses.model.DaySchedule
import com.example.virtualclasses.model.Schedule
import com.example.virtualclasses.ui.fragments.ScheduleDialogFragment
import kotlinx.android.synthetic.main.item_schedule.view.*

class ViewSubscribedRoomScheduleAdapter(val daySchedule: DaySchedule, val context: Context, val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<ViewSubscribedRoomScheduleAdapter.ViewSubscribedRoomScheduleHolder>() {
    class ViewSubscribedRoomScheduleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            viewSubscribedRoomScheduleAdapter: ViewSubscribedRoomScheduleAdapter,
            schedule: Schedule,
            position: Int,
            fragmentManager: FragmentManager
        ){
            itemView.startTimeTextView.text =
                "${schedule.startTime.hour}:${schedule.startTime.minute} ${schedule.startTime.midday}"
            itemView.endTimeTextView.text =
                "${schedule.endTime.hour}:${schedule.endTime.minute} ${schedule.endTime.midday}"
            itemView.scheduleTitle.text = schedule.title
            itemView.scheduleDescription.text = schedule.description
            itemView.setOnClickListener {
                ScheduleDialogFragment{
                    schedule.updateWith(it)
                    viewSubscribedRoomScheduleAdapter.notifyDataSetChanged()
                }.show(fragmentManager, "edit schedule")
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        ViewSubscribedRoomScheduleHolder(
            LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false)
        )

    override fun onBindViewHolder(holder: ViewSubscribedRoomScheduleHolder, position: Int) {
        holder.bind(this, daySchedule.schedules[position], position, fragmentManager)
    }

    override fun getItemCount() = daySchedule.schedules.size
}