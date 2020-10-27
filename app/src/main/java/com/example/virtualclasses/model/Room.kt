package com.example.virtualclasses.model

import java.io.Serializable

interface Room: Serializable {
    var roomId: String
    var roomTitle: String
    var roomDescription: String
    var ownerId: String
}