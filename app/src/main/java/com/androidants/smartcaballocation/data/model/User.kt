package com.androidants.smartcaballocation.data.model

import java.util.jar.Attributes.Name

data class User(
    val name : String ,
    val phone : String ,
    val email : String ,
    val type : Int
)

// Type
// 0 - User
// 1 - Driver
