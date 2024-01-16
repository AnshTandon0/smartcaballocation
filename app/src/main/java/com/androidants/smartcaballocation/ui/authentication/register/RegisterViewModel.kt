package com.androidants.smartcaballocation.ui.authentication.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidants.smartcaballocation.data.model.User
import com.androidants.smartcaballocation.ui.authentication.register.usecases.CreateUserUseCase
import com.androidants.smartcaballocation.ui.authentication.register.usecases.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {
    private var _registerStatus = MutableLiveData<Boolean>()
    private var _createUserStatus = MutableLiveData<Boolean> ( )
    val registerStatus : MutableLiveData<Boolean> by lazy {
        _registerStatus
    }
    val createUserStatus : MutableLiveData<Boolean> by lazy {
        _createUserStatus
    }


    suspend fun register(email: String , password: String) {
        _registerStatus.value = registerUseCase.invoke(email , password)
    }

    suspend fun createUser( user: User )
    {
        _createUserStatus.value = createUserUseCase.invoke(user)
    }

}