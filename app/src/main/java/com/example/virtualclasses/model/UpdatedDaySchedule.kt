package com.example.virtualclasses.model

import java.util.*


class UpdatedDaySchedule(
    override var schedules: MutableList<Schedule>,
    override val roomId: String,
    override val ownerId: String,
    val date: Date
) : DaySchedule{
    constructor():this(mutableListOf(), "", "", Date(0))
}