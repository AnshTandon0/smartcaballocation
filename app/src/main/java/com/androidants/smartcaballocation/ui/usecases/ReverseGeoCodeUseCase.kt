package com.androidants.smartcaballocation.ui.usecases

import com.androidants.smartcaballocation.data.model.GeoCodeResponse
import com.androidants.smartcaballocation.data.repository.MainRepository
import javax.inject.Inject

class ReverseGeoCodeUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun invoke(lat : Double , lon : Double , apiKey : String) : GeoCodeResponse{
        return repository.reverseGeocode(lat , lon , apiKey)
    }
}