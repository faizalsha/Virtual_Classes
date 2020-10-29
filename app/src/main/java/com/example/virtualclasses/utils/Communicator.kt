package com.example.virtualclasses.utils

import androidx.lifecycle.ViewModel
import com.example.virtualclasses.model.DaySchedule
import com.example.virtualclasses.model.Room

object Communicator: ViewModel() {
    var room: Room? = null
    var dayOfWeekIndex: Int? = null
    var daySchedule: DaySchedule? = null
    var default_updated: Int? = null
}