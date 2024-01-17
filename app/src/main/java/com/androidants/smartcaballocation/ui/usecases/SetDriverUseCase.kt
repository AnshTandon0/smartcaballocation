package com.androidants.smartcaballocation.ui.usecases

import com.androidants.smartcaballocation.data.model.Driver
import com.androidants.smartcaballocation.data.repository.MainRepository
import javax.inject.Inject

class SetDriverUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun invoke (driver: Driver) : Boolean {
        return repository.setLocation(driver)
    }
}