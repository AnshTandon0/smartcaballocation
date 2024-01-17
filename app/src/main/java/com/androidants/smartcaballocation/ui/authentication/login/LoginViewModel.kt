package com.androidants.smartcaballocation.ui.authentication.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidants.smartcaballocation.ui.usecases.LoginUseCase
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
    var loadingStatus = MutableLiveData<Int>()

    suspend fun login ( email : String , password : String )
    {
        loadingStatus.postValue( View.VISIBLE )
        _loginStatus.postValue(loginUseCase.invoke(email , password))
        loadingStatus.postValue(View.GONE)
    }

}