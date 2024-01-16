package com.androidants.smartcaballocation.ui.authentication.register.usecases

import com.androidants.smartcaballocation.data.model.User
import com.androidants.smartcaballocation.data.repository.MainRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend fun invoke (user: User) : Boolean {
        return repository.addUserDetail(user)
    }
}