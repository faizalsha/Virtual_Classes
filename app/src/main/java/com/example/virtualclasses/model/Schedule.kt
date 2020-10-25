package com.example.virtualclasses.model


data class Schedule(
    val title: String,
    val description: String,
    val meetingLink: String,
    val additionalInfo: String,
    val startTime: ScheduleTime,
    val endTime: ScheduleTime
)