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
    override var schedules: MutableList<Schedule>,
    override var roomId: String,
    override var ownerId: String,
    var day: WeekDay
) : DaySchedule{
    constructor():this(mutableListOf(), "", "", WeekDay.MONDAY)
    fun updateWith(value: DefaultDaySchedule){
        schedules = value.schedules
        roomId = value.roomId
        ownerId = value.ownerId
        day = value.day
    }
}