package com.androidants.smartcaballocation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.data.model.GeoCodeResponse
import com.androidants.smartcaballocation.data.model.User
import com.androidants.smartcaballocation.ui.usecases.GetDriversUseCase
import com.androidants.smartcaballocation.ui.usecases.ReverseGeoCodeUseCase
import com.androidants.smartcaballocation.ui.usecases.SetDriverUseCase
import com.androidants.smartcaballocation.ui.usecases.SetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val setDriverUseCase: SetDriverUseCase ,
    private val getDriversUseCase: GetDriversUseCase ,
    private val reverseGeoCodeUseCase: ReverseGeoCodeUseCase
) : ViewModel() {

    private var _driverList = MutableLiveData<List<Driver>?>()
    private var _setDriverStatus = MutableLiveData<Boolean>()
    private var _geocodeResponse = MutableLiveData<GeoCodeResponse>()
    val driverList : MutableLiveData<List<Driver>?> by lazy {
        _driverList
    }
    val setDriverStatus : MutableLiveData<Boolean> by lazy {
        _setDriverStatus
    }
    val geocodeResponse : MutableLiveData<GeoCodeResponse> by lazy {
        _geocodeResponse
    }

    suspend fun getDrivers(pincode : String) {
        _driverList.postValue(getDriversUseCase.invoke(pincode))
    }

    suspend fun setDriver(driver: Driver){
        _setDriverStatus.value = setDriverUseCase.invoke(driver)
    }

    suspend fun reverseGeocode( lat : Double , lon : Double , apiKey : String ){
        _geocodeResponse.postValue(reverseGeoCodeUseCase.invoke(lat , lon , apiKey))
    }

}