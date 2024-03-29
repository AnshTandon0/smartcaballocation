package com.androidants.smartcaballocation.ui.usecases

import com.androidants.smartcaballocation.data.repository.MainRepository
import javax.inject.Inject

class CheckAuthStatusUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun invoke () : Boolean {
        return repository.checkUserAuthStatus()
    }
}