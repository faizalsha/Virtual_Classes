package com.example.virtualclasses.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualclasses.R
import com.example.virtualclasses.model.Room
import kotlinx.android.synthetic.main.item_room.view.*

class RoomsAdapter(private val data: ArrayList<Room>, val context: Context, private val listener: (Room) -> Unit): RecyclerView.Adapter<RoomsAdapter.MyAdapterViewHolder>() {
    class MyAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Room, listener: (Room) -> Unit){
            itemView.roomTitle.text = item.roomTitle
            itemView.roomDescription.text = item.roomDescription
            itemView.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_room, parent, false))

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemCount() = data.size
}