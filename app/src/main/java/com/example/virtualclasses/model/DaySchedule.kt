package com.example.virtualclasses.model

interface DaySchedule {
    var schedules: List<Schedule>
    val roomId: String
    val ownerId: String
}