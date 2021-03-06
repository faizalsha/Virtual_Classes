package com.example.virtualclasses.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.example.virtualclasses.model.*
import java.util.*

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
    fun getCurrentDayOfWeekIndex(): Int {
        //1 based indexing Calendar.SUNDAY -> 1, Calendar.MONDAY -> 2
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
    }

    fun selectTime(context: Context, onTimeSelected: (ScheduleTime) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        val listener = TimePickerDialog.OnTimeSetListener { timerView, h, m ->
            var AM_PM = MidDay.AM
            var hour = h
            var min = m
            if (h >= 12) AM_PM = MidDay.PM
            if (h > 12) hour -= 12
            if (h == 0) hour += 12
            onTimeSelected(ScheduleTime(hour.toString(), min.toString(), AM_PM))
        }
        val timePickerDialog = TimePickerDialog(context, listener, hour, minute, false)
        timePickerDialog.show()
    }

    fun selectDate(context: Context, onDateSelected: (Date) -> Unit) {
        val myCalendar = Calendar.getInstance()
        val listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            myCalendar.set(Calendar.HOUR_OF_DAY, 0)
            myCalendar.set(Calendar.MINUTE, 0)
            myCalendar.set(Calendar.SECOND, 0)
            myCalendar.set(Calendar.MILLISECOND, 0)
            onDateSelected(myCalendar.time)
        }
        DatePickerDialog(
            context,
            listener,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


    fun validateSchedule(daySchedule: DaySchedule, scheduleToBeInserted: Schedule): Boolean {
        //start time and end time must be valid
        if (scheduleToBeInserted.startTime.equals(scheduleToBeInserted.endTime)) return false
        val scheduleList = daySchedule.schedules
        if (scheduleList.size == 0) return true
        scheduleList.sort()
        if (scheduleToBeInserted.endTime.isBeforeOrEqual(scheduleList[0].startTime)) return true
        for (i in 1 until scheduleList.size) {
            if (scheduleList[i - 1].endTime.isBeforeOrEqual(scheduleToBeInserted.startTime)
                && scheduleToBeInserted.endTime.isBeforeOrEqual(scheduleList[i].startTime)
            )
                return true
        }
        if (scheduleList[scheduleList.size - 1].endTime.isBeforeOrEqual(scheduleToBeInserted.startTime)) return true
        return false
    }

    fun getNextWeekDay(date: Date, day: WeekDay): Date {
        val date1 = GregorianCalendar(date.year, date.month - 1, date.date)

        while (date1[Calendar.DAY_OF_WEEK] != weekDayToCalenderDay[day]) {
            date1.add(Calendar.DATE, 1)
        }
        return Date(
            date1.get(Calendar.YEAR),
            date1.get(Calendar.MONTH) + 1,
            date1.get(Calendar.DAY_OF_MONTH)
        )
    }
    fun getCurrentOrNextWeekDayDate(date: Calendar, day: WeekDay): Date{
        date.set(Calendar.HOUR_OF_DAY, 0)
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
        while (date[Calendar.DAY_OF_WEEK] != weekDayToCalenderDay[day]){
            date.add(Calendar.DATE, 1)
        }
        return date.time
    }

    private val weekDayToCalenderDay = mapOf(
        WeekDay.MONDAY to Calendar.MONDAY,
        WeekDay.TUESDAY to Calendar.TUESDAY,
        WeekDay.WEDNESDAY to Calendar.WEDNESDAY,
        WeekDay.THURSDAY to Calendar.THURSDAY,
        WeekDay.FRIDAY to Calendar.FRIDAY,
        WeekDay.SATURDAY to Calendar.SATURDAY,
        WeekDay.SUNDAY to Calendar.SUNDAY
    )

    fun getCurrentDate(): Date{
        val date = Date()
        return Date(date.year, date.month, date.date)
    }
    fun whatIsTheDate(): Date{
        val date = Calendar.getInstance()
        date.set(Calendar.HOUR_OF_DAY, 0)
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
        return date.time
    }
    fun getCurrentCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar
    }
}