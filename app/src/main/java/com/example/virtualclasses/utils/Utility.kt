package com.example.virtualclasses.utils

import android.app.TimePickerDialog
import android.content.Context
import android.media.midi.MidiDevice
import com.example.virtualclasses.model.*
import java.util.*
import kotlin.math.min

object Utility {
    val weekDayToIndex = mapOf<WeekDay, Int>(
        WeekDay.SUNDAY to 0,
        WeekDay.MONDAY to 1,
        WeekDay.TUESDAY to 2,
        WeekDay.WEDNESDAY to 3,
        WeekDay.THURSDAY to 4,
        WeekDay.FRIDAY to 5,
        WeekDay.SATURDAY to 6
    )
    val indexToWeekDay = mapOf(
        0 to WeekDay.SUNDAY,
        1 to WeekDay.MONDAY,
        2 to WeekDay.TUESDAY,
        3 to WeekDay.WEDNESDAY,
        4 to WeekDay.THURSDAY,
        5 to WeekDay.FRIDAY,
        6 to WeekDay.SATURDAY
    )
    fun getCurrentDayOfWeekIndex(): Int{
        //1 based indexing Calendar.SUNDAY -> 1, Calendar.MONDAY -> 2
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    }
    fun selectTime(context: Context, onTimeSelected: (ScheduleTime)->Unit){
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



    fun validateSchedule(daySchedule: DaySchedule, scheduleToBeInserted: Schedule): Boolean{
        //start time and end time must be valid
        if(scheduleToBeInserted.startTime.equals(scheduleToBeInserted.endTime)) return false
        val scheduleList = daySchedule.schedules
        if(scheduleList.size == 0) return true
        scheduleList.sort()
        if(scheduleToBeInserted.endTime.isBeforeOrEqual(scheduleList[0].startTime)) return true
        for(i in 1 until scheduleList.size){
            if(scheduleList[i - 1].endTime.isBeforeOrEqual(scheduleToBeInserted.startTime)
                && scheduleToBeInserted.endTime.isBeforeOrEqual(scheduleList[i].startTime))
                return true
        }
        if (scheduleList[scheduleList.size - 1].endTime.isBeforeOrEqual(scheduleToBeInserted.startTime)) return true
        return false
    }
}