package com.example.virtualclasses.model

data class Student( val name: String,
                    val email:String,
                    val phone: Long?,
                    val roll: String?,
                    val isVerified: Boolean) {
    constructor():this("", "", null, null, false)
}