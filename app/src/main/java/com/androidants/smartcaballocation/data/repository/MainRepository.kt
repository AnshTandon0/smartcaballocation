package com.androidants.smartcaballocation.data.repository

import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.data.model.GeoCodeResponse
import com.androidants.smartcaballocation.data.model.User


interface MainRepository {

    // authentication

    suspend fun checkUserAuthStatus() : Boolean

    suspend fun login(email: String , password : String) : Boolean

    suspend fun register(email: String , password : String) : Boolean

    suspend fun logout() : Boolean

    // user profile details

    suspend fun setUserDetail(user: User) : Boolean

    suspend fun getUserDetail(email: String) : User?

    // user location

    suspend fun setDriver(driver : Driver ) : Boolean

    suspend fun getDrivers(pincode: String) : List<Driver>?

    suspend fun getAllDrivers(): List<Driver>?


    // revert geocoding

    suspend fun reverseGeocode (lat : Double , lon : Double , apiKey : String) : GeoCodeResponse

}