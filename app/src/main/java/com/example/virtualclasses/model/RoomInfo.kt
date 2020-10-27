package com.example.virtualclasses.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomInfo(
    override var roomId: String,
    override var roomTitle: String,
    override var roomDescription: String,
    override var ownerId: String
): Room, Parcelable {
    constructor():this("", "", "", "")
}