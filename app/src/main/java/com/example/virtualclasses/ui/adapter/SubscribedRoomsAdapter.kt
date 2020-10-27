package com.example.virtualclasses.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualclasses.R
import com.example.virtualclasses.model.RoomInfo
import kotlinx.android.synthetic.main.item_subscribed_room.view.*

class SubscribedRoomsAdapter(
    private val data: MutableList<RoomInfo>,
    private val context: Context) : RecyclerView.Adapter<SubscribedRoomsAdapter.SubscribedRoomHolder>() {
    class SubscribedRoomHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: RoomInfo, position: Int){
            itemView.subscribedRoomId.text = item.roomId
            itemView.subscribedRoomOwnerId.text = item.ownerId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribedRoomHolder =
        SubscribedRoomHolder(LayoutInflater.from(context).inflate(R.layout.item_subscribed_room, parent, false))

    override fun onBindViewHolder(holder: SubscribedRoomHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount() = data.size
}