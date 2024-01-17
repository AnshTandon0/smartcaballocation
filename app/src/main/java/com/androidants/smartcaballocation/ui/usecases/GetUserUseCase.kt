package com.androidants.smartcaballocation.ui.usecases

import com.androidants.smartcaballocation.data.model.User
import com.androidants.smartcaballocation.data.repository.MainRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun invoke (email : String) : User? {
        return repository.getUserDetail(email)
    }
}