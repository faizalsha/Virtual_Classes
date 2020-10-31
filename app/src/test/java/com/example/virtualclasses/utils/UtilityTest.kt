package com.example.virtualclasses.utils

import com.example.virtualclasses.model.WeekDay
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class UtilityTest {

    @Test
    fun getNextWeekDay() {
        val date = Date(2020, 10, 29)
        val weekDay = WeekDay.TUESDAY
        val result = Utility.getNextWeekDay(date, weekDay)
        println("date: ${result.date} month: ${result.month} day: ${result.day} year: ${date.year}")
    }

    @Test
    fun getCurrentDate(){
        val date = Date()
        println("date: ${date.date} month: ${date.month} year: ${date.year + 1900}")
    }

    @Test
    fun getCurrentDateUsingCalendar(){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.DATE, 1)
        println(calendar.time)
    }
    @Test
    fun getNextWeekDate(){
        val calendar = Calendar.getInstance()
        println(Utility.getCurrentOrNextWeekDayDate(calendar, WeekDay.WEDNESDAY))
    }
    @Test
    fun getDate(){
        val date = Utility.whatIsTheDate()
        println(date)
    }
}