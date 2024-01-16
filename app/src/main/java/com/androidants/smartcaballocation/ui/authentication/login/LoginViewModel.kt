package com.androidants.smartcaballocation.ui.authentication.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidants.smartcaballocation.ui.authentication.login.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private var _loginStatus = MutableLiveData<Boolean>()
    val loginStatus : MutableLiveData<Boolean> by lazy {
        _loginStatus
    }

    suspend fun login ( email : String , password : String )
    {
        _loginStatus.value = loginUseCase.invoke(email , password)
    }

}