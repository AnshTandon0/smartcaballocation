package com.androidants.smartcaballocation.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidants.smartcaballocation.ui.usecases.CheckAuthStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModal @Inject constructor(
    private val checkAuthStatusUseCase: CheckAuthStatusUseCase
): ViewModel() {

    private var _authStatus = MutableLiveData<Boolean>(true)
    val authStatus : MutableLiveData<Boolean> by lazy {
        _authStatus
    }

    init {
        viewModelScope.launch{
            checkAuthState()
        }
    }

    suspend fun checkAuthState () {
        _authStatus.value = checkAuthStatusUseCase.invoke()
    }
}