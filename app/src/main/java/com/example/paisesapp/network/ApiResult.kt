package com.example.paisesapp.network

sealed class ApiResult<out T> {
    class Success<T>(val data: T?): ApiResult<T>()
    class Error(val message:String): ApiResult<Nothing>()
}