package com.example.virtualclasses.utils

import android.app.TimePickerDialog
import android.content.Context
import android.media.midi.MidiDevice
import com.example.virtualclasses.model.MidDay
import com.example.virtualclasses.model.ScheduleTime
import java.util.*
import kotlin.math.min

object Utility {
    public fun selectTime(context: Context, onTimeSelected: (ScheduleTime)->Unit){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        val listener = TimePickerDialog.OnTimeSetListener { timerView, h, m ->
            var AM_PM = MidDay.AM
            var hour = h
            var min = m
            if(h >= 12) AM_PM = MidDay.PM
            if(h > 12) hour -= 12
            if(h == 0) hour += 12
            onTimeSelected(ScheduleTime(hour.toString(), min.toString(), AM_PM))
        }
        val timePickerDialog = TimePickerDialog(context, listener, hour, minute, false)
        timePickerDialog.show()
    }
}