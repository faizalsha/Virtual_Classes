package com.example.virtualclasses.model

data class User(private val name: String, private val email:String, private val phone: Long?, private val roll: String?) {
    constructor():this("", "", null, null)
}