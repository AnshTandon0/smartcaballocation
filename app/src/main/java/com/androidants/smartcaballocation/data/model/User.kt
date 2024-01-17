package com.androidants.smartcaballocation.data.model

import android.os.Parcelable


data class User(
    var name : String ,
    var phone : String ,
    var email : String ,
    var type : Int
) : java.io.Serializable

// Type
// 0 - User
// 1 - Driver
