package com.example.virtualclasses.model

import java.sql.Time

data class Schedule(
    val title: String,
    val Description: String,
    val meetingLink: String,
    val additionalInfo: String,
    val startTime: Time,
    val endTime: Time
)