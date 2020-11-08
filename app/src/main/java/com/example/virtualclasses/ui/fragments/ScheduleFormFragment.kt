package com.example.virtualclasses.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.virtualclasses.R
import com.example.virtualclasses.firebase.FireStore
import com.example.virtualclasses.local.Constants
import com.example.virtualclasses.model.*
import com.example.virtualclasses.utils.Communicator
import com.example.virtualclasses.utils.Utility
import kotlinx.android.synthetic.main.fragment_schedule_form.*
import java.util.*

class ScheduleFormFragment : Fragment() {
//    private val args: ScheduleFormFragmentArgs by navArgs()
//    lateinit var daySchedule: DaySchedule
    lateinit var to: ScheduleTime
    lateinit var from: ScheduleTime
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = Calendar.getInstance()
        from = ScheduleTime(calendar.get(Calendar.HOUR).toString(), calendar.get(Calendar.MINUTE).toString(), MidDay.AM)
        to = ScheduleTime(calendar.get(Calendar.HOUR).toString(), calendar.get(Calendar.MINUTE).toString(), MidDay.AM)
        fromTextView.text = from.toString()
        toTextView.text = to.toString()
        setupListener()
        dayName.text = Utility.indexToWeekDay[Communicator.dayOfWeekIndex].toString()
        if(Communicator.default_updated == 1)
            dayName.append("(Default)")
    }
    private fun setupListener(){
        insertSchedule.setOnClickListener {
            if(!validateForm()){
                Toast.makeText(context, "invalid fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val schedule = Schedule(
                titleEditText.text.toString(),
                descriptionEditText.text.toString(),
                meetingLinkEditText.text.toString(),
                additionalInfoEditText.text.toString(),
                from,
                to
            )
            if(Utility.validateSchedule(Communicator.daySchedule!!, schedule)){
                Communicator.daySchedule!!.schedules.add(schedule)
                if(Communicator.default_updated == 0) {
                    //updated schedule selected
                    val date = Communicator.date!!
                    val updatedDaySchedule =
                        UpdatedDaySchedule(
                            Communicator.daySchedule!!.schedules,
                            Communicator.room!!.roomId,
                            Communicator.room!!.ownerId,
                            date
                        )
                    Communicator.isFirebaseLoading = true
                    FireStore.saveUpdatedDaySchedule(
                        updatedDaySchedule,
                        Communicator.room!!.roomId
                    ) {
                        if (it)
                            Toast.makeText(context, "Saved updated schedule successfully", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(
                                context,
                                "updatedSchedule couldn't saved successfully",
                                Toast.LENGTH_LONG
                            ).show()
                        Communicator.isFirebaseLoading = false
                    }
                }else{
                    //default schedule selected
                    val dds =
                        DefaultDaySchedule(
                            Communicator.daySchedule!!.schedules,
                            Communicator.room!!.roomId,
                            Communicator.room!!.ownerId,
                            Utility.indexToWeekDay[Communicator.dayOfWeekIndex]!!
                        )
                    Communicator.isFirebaseLoading = true
                    FireStore
                        .saveDefaultDaySchedule(
                            Communicator.dayOfWeekIndex!!,
                            dds,
                            Communicator.room!!.roomId){
                                if(it){
                                    Toast.makeText(context, "saved default schedule successfully", Toast.LENGTH_LONG).show()
                                }else{
                                    Toast.makeText(context, "couldn't save successfully", Toast.LENGTH_LONG).show()
                                }
                                Communicator.isFirebaseLoading = false
                            }
                }
                Toast.makeText(context, "done", Toast.LENGTH_LONG).show()
//                activity?.onBackPressed()
            }else{
                Toast.makeText(context, "conflicting Schedule", Toast.LENGTH_LONG).show()
            }

        }
        close.setOnClickListener {
            activity?.onBackPressed()
        }
        fromTextView.setOnClickListener {
            if(context == null) return@setOnClickListener
            Utility.selectTime(requireContext()){
                fromTextView.text = it.toString()
                from = it
            }
        }
        toTextView.setOnClickListener {
            if(context == null) return@setOnClickListener
            Utility.selectTime(requireContext()){
                toTextView.text = it.toString()
                to = it
            }
        }
    }
    private fun validateForm():Boolean{
        if(titleEditText.text.toString().trim().isEmpty()) return false
        if(toTextView.text.toString() == Constants.SELECT_TIME ||
            fromTextView.text.toString() == Constants.SELECT_TIME) return false
        if(!from.isBefore(to)) return false
        return true
    }
}