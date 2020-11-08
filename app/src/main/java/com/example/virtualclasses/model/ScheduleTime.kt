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
    fun isBefore(scheduleTime: ScheduleTime): Boolean{
        if(this.midday == MidDay.AM && scheduleTime.midday == MidDay.PM) return true
        val hour = Integer.parseInt(this.hour) % 12
        val scheduleHour = Integer.parseInt(scheduleTime.hour)
        if(hour < scheduleHour) return true
        if(hour == scheduleHour && this.minute < scheduleTime.minute) return true
        return false
    }
    fun isBeforeOrEqual(scheduleTime: ScheduleTime):Boolean{
        if(!this.isBefore(scheduleTime) && !scheduleTime.isBefore(this)) return true
        return  this.isBefore(scheduleTime)
    }

    override fun equals(other: Any?): Boolean {
        if(other == null || other !is ScheduleTime) return false
        if(this.hour == other.hour && this.minute == other.minute && this.midday == this.midday) return true
        return false
    }

    override fun toString(): String {
        return "$hour:$minute $midday"
    }
    constructor():this("00", "00", MidDay.AM)
}