package com.example.virtualclasses.model


data class Schedule(
    var title: String,
    var description: String,
    var meetingLink: String,
    var additionalInfo: String,
    var startTime: ScheduleTime,
    var endTime: ScheduleTime
){
    constructor():this("", "", "", "", ScheduleTime(), ScheduleTime())
    fun updateWith(schedule: Schedule){
        this.title = schedule.title
        this.description = schedule.description
        this.meetingLink = schedule.meetingLink
        this.additionalInfo = schedule.additionalInfo
        this.startTime = schedule.startTime
        this.endTime = schedule.endTime
    }
}