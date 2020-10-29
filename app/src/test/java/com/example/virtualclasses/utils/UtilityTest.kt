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
}