package com.androidants.smartcaballocation.common

import kotlinx.coroutines.CoroutineExceptionHandler

object Constants {
    val PERMISSION_REQUEST_CODE = 101
    val BASE_URL = "https://api.geoapify.com/"
    val API_KEY = "3ab6f11fc6c44b31ab88d9bd5a49498d"
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }
}