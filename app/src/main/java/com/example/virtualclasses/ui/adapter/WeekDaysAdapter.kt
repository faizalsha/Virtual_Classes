package com.example.virtualclasses.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualclasses.R
import com.example.virtualclasses.model.RoomDetails
import com.example.virtualclasses.ui.fragments.WeekDaysFragmentDirections
import kotlinx.android.synthetic.main.item_day.view.*

//class WeekDaysAdapter(private val weekDays: ArrayList<String>, val roomDetails: RoomDetails, val context: Context): RecyclerView.Adapter<WeekDaysAdapter.WeekDaysHolder>() {
//    class WeekDaysHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(day: String, roomDetails: RoomDetails, position: Int){
//            itemView.day.text = day
//            itemView.dayDetail.text = day + "with details"
//            itemView.setOnClickListener {
//                val action = WeekDaysFragmentDirections.actionWeekDaysFragmentToEditPerDayScheduleFragment(position, roomDetails)
//                it.findNavController().navigate(action)
//            }
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDaysHolder =
//        WeekDaysHolder(LayoutInflater.from(context).inflate(R.layout.item_day, parent, false))
//
//    override fun onBindViewHolder(holder: WeekDaysHolder, position: Int) {
//        holder.bind(weekDays[position], roomDetails, position)
//    }
//
//    override fun getItemCount() = 7
//}