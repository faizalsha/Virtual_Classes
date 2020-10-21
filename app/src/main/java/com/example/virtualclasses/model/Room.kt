package com.example.virtualclasses.model

data class Room(
    var roomId: String,
    val roomTitle: String,
    val roomDescription: String,
    val roomOwner: String,
    val roomOwnerUserId: String,
    val roomOwnerEmail:String,
    val subscribedUser: Int,
    val createdAt: String) {
    constructor():this("", "",  "" ,"", "", "", 0, "")
}