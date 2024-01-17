package com.androidants.smartcaballocation.ui.addDriver

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.data.model.GeoCodeResponse
import com.androidants.smartcaballocation.ui.usecases.ReverseGeoCodeUseCase
import com.androidants.smartcaballocation.ui.usecases.SetDriverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddDriverViewModel @Inject constructor(
    private val setDriverUseCase: SetDriverUseCase,
    private val reverseGeoCodeUseCase: ReverseGeoCodeUseCase,
) : ViewModel() {

    private var _setDriverStatus = MutableLiveData<Boolean>()
    private var _geocodeResponse = MutableLiveData<GeoCodeResponse>()
    val setDriverStatus : MutableLiveData<Boolean> by lazy {
        _setDriverStatus
    }
    val geocodeResponse : MutableLiveData<GeoCodeResponse> by lazy {
        _geocodeResponse
    }
    var loadingStatus = MutableLiveData<Int>()

    suspend fun reverseGeocode( lat : Double , lon : Double , apiKey : String ){
        loadingStatus.postValue( View.VISIBLE )
        _geocodeResponse.postValue(reverseGeoCodeUseCase.invoke(lat , lon , apiKey))
        loadingStatus.postValue( View.GONE )
    }

    suspend fun setDriver(driver: Driver){
        loadingStatus.postValue( View.VISIBLE )
        _setDriverStatus.postValue(setDriverUseCase.invoke(driver))
        loadingStatus.postValue( View.GONE )
    }

}