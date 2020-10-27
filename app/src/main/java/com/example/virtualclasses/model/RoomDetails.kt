package com.example.virtualclasses.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomDetails(
    override var roomId: String,
    override var roomTitle: String,
    override var roomDescription: String,
    override var ownerId: String,
    val roomOwnerUserId: String,
    val roomOwnerEmail:String,
    val subscribedUser: Int,
    val createdAt: String) :Room,  Parcelable {
    constructor():this("", "",  "" ,"", "", "", 0, "")
}