package com.example.virtualclasses.model


data class Schedule(
    var title: String,
    var description: String,
    var meetingLink: String,
    var additionalInfo: String,
    var startTime: ScheduleTime,
    var endTime: ScheduleTime
): Comparable<Schedule>{
    constructor():this("", "", "", "", ScheduleTime(), ScheduleTime())
    fun updateWith(schedule: Schedule){
        this.title = schedule.title
        this.description = schedule.description
        this.meetingLink = schedule.meetingLink
        this.additionalInfo = schedule.additionalInfo
        this.startTime = schedule.startTime
        this.endTime = schedule.endTime
    }

    override fun compareTo(other: Schedule): Int {
        if(this.startTime.isBefore(other.startTime)) return -1
        if(other.startTime.isBefore(this.startTime)) return 1
        return 0
    }
}