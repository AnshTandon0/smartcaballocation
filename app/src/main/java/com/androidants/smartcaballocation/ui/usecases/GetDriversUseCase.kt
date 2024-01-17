package com.androidants.smartcaballocation.ui.usecases

import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.data.repository.MainRepository
import javax.inject.Inject

class GetDriversUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun invoke (pincode: String) : List<Driver>? {
        return repository.getLocations(pincode)
    }
}