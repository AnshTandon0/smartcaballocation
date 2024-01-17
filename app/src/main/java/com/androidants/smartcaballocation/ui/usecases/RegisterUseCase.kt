package com.androidants.smartcaballocation.ui.usecases

import com.androidants.smartcaballocation.data.repository.MainRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun invoke (email : String , password : String) : Boolean {
        return repository.register(email , password)
    }
}