package com.androidants.smartcaballocation.ui.authentication.register

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidants.smartcaballocation.data.model.User
import com.androidants.smartcaballocation.ui.usecases.SetUserUseCase
import com.androidants.smartcaballocation.ui.usecases.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val setUserUseCase: SetUserUseCase
) : ViewModel() {
    private var _registerStatus = MutableLiveData<Boolean>()
    private var _createUserStatus = MutableLiveData<Boolean> ( )
    val registerStatus : MutableLiveData<Boolean> by lazy {
        _registerStatus
    }
    val createUserStatus : MutableLiveData<Boolean> by lazy {
        _createUserStatus
    }
    var loadingStatus = MutableLiveData<Int>()

    suspend fun register(email: String , password: String) {
        loadingStatus.postValue( View.VISIBLE )
        _registerStatus.postValue(registerUseCase.invoke(email , password))
        loadingStatus.postValue( View.GONE )
    }

    suspend fun createUser( user: User )
    {
        loadingStatus.postValue( View.VISIBLE )
        _createUserStatus.postValue(setUserUseCase.invoke(user))
        loadingStatus.postValue( View.GONE )
    }

}