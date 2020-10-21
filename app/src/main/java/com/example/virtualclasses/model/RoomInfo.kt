package com.example.virtualclasses.model

data class RoomInfo(val userOwnerId: String, val roomId: String){
    constructor():this("", "")
}