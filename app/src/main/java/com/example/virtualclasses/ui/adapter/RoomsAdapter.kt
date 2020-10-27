package com.example.virtualclasses.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualclasses.R
import com.example.virtualclasses.model.Room
import com.example.virtualclasses.model.RoomDetails
import com.example.virtualclasses.ui.fragments.MyRoomsFragmentDirections
import kotlinx.android.synthetic.main.item_room.view.*

class RoomsAdapter(
    private val data: MutableList<Room>,
    private val context: Context
) : RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Room, position: Int){
            itemView.roomTitle.text = item.roomTitle
            itemView.roomDescription.text = item.roomId
            itemView.setOnClickListener {
//                val action =
//                    MyRoomsFragmentDirections.actionMyRoomsFragmentToWeekDaysFragment(item)
//                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder =
        RoomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_room, parent, false))


    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount() = data.size
}