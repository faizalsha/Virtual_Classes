package com.example.virtualclasses.model

data class RoomInfo(
    override var roomId: String,
    override var roomTitle: String,
    override var roomDescription: String,
    override var ownerId: String
): Room{
    constructor():this("", "", "", "")
}