package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireAuth
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.local.Constants
import com.example.virtualclasses.local.SharedPrefsUtils
import com.example.virtualclasses.model.Room
import com.example.virtualclasses.ui.adapter.RoomsAdapter
import com.example.virtualclasses.utils.Communicator
import kotlinx.android.synthetic.main.fragment_select_room.*

class SelectRoomFragment : Fragment() {
    private lateinit var roomsAdapter: RoomsAdapter
    private val rooms : ArrayList<Room> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFirebase()
    }
    private fun setupRecyclerView(){
        if(context == null) return
        roomsAdapter = RoomsAdapter(rooms, requireContext(), listener = {
            SharedPrefsUtils.saveObject(Constants.HOME_ROOM, it, context)
            activity?.onBackPressed()
        }, detail = {
        })
        rvSelectRoomFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = roomsAdapter
        }
    }
    private fun setupFirebase(){
        Communicator.isFirebaseLoading = true
        val userId = FireAuth.getCurrentUser()!!.uid
        FireStore.getSubscribedRooms(userId){
            if(it != null){
                it.forEach {room ->
                    rooms.add(room)
                }
                roomsAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(context, "no room found", Toast.LENGTH_LONG).show()
                imgNoRoomFound.visibility = View.VISIBLE
            }
            Communicator.isFirebaseLoading = false
            pbSelectRoomFragment.visibility = View.GONE
        }
    }
}