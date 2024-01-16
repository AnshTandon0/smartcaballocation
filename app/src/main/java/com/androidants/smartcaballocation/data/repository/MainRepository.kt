package com.androidants.smartcaballocation.data.repository

import androidx.lifecycle.MutableLiveData
import com.androidants.smartcaballocation.data.model.User

interface MainRepository {

    suspend fun checkUserAuthStatus() : Boolean

    suspend fun login(email: String , password : String) : Boolean

    suspend fun register(email: String , password : String) : Boolean

    suspend fun logout() : Boolean

    suspend fun addUserDetail(user: User) : Boolean

}