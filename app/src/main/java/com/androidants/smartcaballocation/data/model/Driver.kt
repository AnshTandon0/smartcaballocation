package com.androidants.smartcaballocation.data.model

data class Driver(
    val name : String = "",
    val email : String = "",
    val phone : String = "",
    val latitute : Float = 0f,
    val longitude : Float = 0f,
    val country : String = "",
    val state : String = "",
    val city : String  = "",
    val pincode : String = "",
    var distance : Double = 0.0,
    var status : Int = 0
)

// status  0-available , 1- busy