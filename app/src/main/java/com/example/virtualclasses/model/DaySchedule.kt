package com.example.virtualclasses.model

interface DaySchedule {
    var schedules: MutableList<Schedule>
    val roomId: String
    val ownerId: String
}