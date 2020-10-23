package com.example.virtualclasses.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(
    var roomId: String,
    val roomTitle: String,
    val roomDescription: String,
    val roomOwner: String,
    val roomOwnerUserId: String,
    val roomOwnerEmail:String,
    val subscribedUser: Int,
    val createdAt: String) : Parcelable {
    constructor():this("", "",  "" ,"", "", "", 0, "")
}