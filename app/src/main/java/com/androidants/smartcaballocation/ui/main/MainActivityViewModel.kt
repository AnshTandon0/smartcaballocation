package com.androidants.smartcaballocation.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.data.model.GeoCodeResponse
import com.androidants.smartcaballocation.data.model.User
import com.androidants.smartcaballocation.ui.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val setDriverUseCase: SetDriverUseCase ,
    private val getDriversUseCase: GetDriversUseCase ,
    private val reverseGeoCodeUseCase: ReverseGeoCodeUseCase ,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private var _driverList = MutableLiveData<List<Driver>?>()
    private var _setDriverStatus = MutableLiveData<Boolean>()
    private var _geocodeResponse = MutableLiveData<GeoCodeResponse>()
    private var _logoutStatus = MutableLiveData<Boolean>()
    val driverList : MutableLiveData<List<Driver>?> by lazy {
        _driverList
    }
    val setDriverStatus : MutableLiveData<Boolean> by lazy {
        _setDriverStatus
    }
    val geocodeResponse : MutableLiveData<GeoCodeResponse> by lazy {
        _geocodeResponse
    }
    val logoutStatus : MutableLiveData<Boolean> by lazy {
        _logoutStatus
    }
    var loadingStatus = MutableLiveData<Int>()

    suspend fun getDrivers(pincode : String) {
        loadingStatus.postValue( View.VISIBLE )
        _driverList.postValue(getDriversUseCase.invoke(pincode))
        loadingStatus.postValue( View.GONE )
    }

    suspend fun setDriver(driver: Driver){
        loadingStatus.postValue( View.VISIBLE )
        _setDriverStatus.postValue(setDriverUseCase.invoke(driver))
        loadingStatus.postValue( View.GONE )
    }

    suspend fun reverseGeocode( lat : Double , lon : Double , apiKey : String ){
        loadingStatus.postValue( View.VISIBLE )
        _geocodeResponse.postValue(reverseGeoCodeUseCase.invoke(lat , lon , apiKey))
        loadingStatus.postValue( View.GONE )
    }

    suspend fun logout ()
    {
        loadingStatus.postValue(View.VISIBLE)
        _logoutStatus.postValue( logOutUseCase.invoke())
        loadingStatus.postValue(View.GONE)
    }

}