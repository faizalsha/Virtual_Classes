package com.example.virtualclasses.model

import java.io.Serializable

enum class WeekDay{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

class DefaultDaySchedule(
    override var schedules: List<Schedule>,
    override val roomId: String,
    override val ownerId: String,
    val day: WeekDay
) : DaySchedule{
    constructor():this(listOf(), "", "", WeekDay.MONDAY)
}