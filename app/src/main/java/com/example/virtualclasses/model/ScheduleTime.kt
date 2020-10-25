package com.example.virtualclasses.model

import kotlin.math.min

enum class MidDay{
    AM,
    PM
}

class ScheduleTime(var hour: String, var minute: String, var midday: MidDay) {
    init {
        if(hour.length == 1) hour = "0$hour"
        if(minute.length == 1) minute = "0$minute"
    }
    public fun isBefore(scheduleTime: ScheduleTime): Boolean{
        if(this.midday == MidDay.AM && scheduleTime.midday == MidDay.PM) return true
        if(this.hour < scheduleTime.hour) return true
        if(this.hour == scheduleTime.hour && this.minute < scheduleTime.minute) return true
        return false
    }
    constructor():this("00", "00", MidDay.AM)
}