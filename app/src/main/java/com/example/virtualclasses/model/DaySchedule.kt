package com.example.virtualclasses.model

import java.io.Serializable

interface DaySchedule: Serializable {
    var schedules: MutableList<Schedule>
    val roomId: String
    val ownerId: String
}