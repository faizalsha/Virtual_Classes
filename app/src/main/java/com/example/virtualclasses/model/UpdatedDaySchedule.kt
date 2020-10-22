package com.example.virtualclasses.model

import java.sql.Date


class UpdatedDaySchedule(
    override var schedules: List<Schedule>,
    override val roomId: String,
    override val ownerId: String,
    val date: Date
) : DaySchedule{
    constructor():this(listOf(), "", "", Date(0))
}