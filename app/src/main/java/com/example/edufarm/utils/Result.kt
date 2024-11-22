package com.example.edufarm.utils

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}

