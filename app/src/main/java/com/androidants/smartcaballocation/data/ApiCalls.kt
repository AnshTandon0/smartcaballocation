package com.androidants.smartcaballocation.data

import com.androidants.smartcaballocation.common.Constants
import com.androidants.smartcaballocation.data.model.GeoCodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCalls {

    @GET("/v1/geocode/reverse")
    suspend fun getCoins(
        @Query("lat") lat : Double ,
        @Query("lon") lon : Double ,
        @Query("apiKey") apiKey : String = Constants.API_KEY
    ): GeoCodeResponse

}