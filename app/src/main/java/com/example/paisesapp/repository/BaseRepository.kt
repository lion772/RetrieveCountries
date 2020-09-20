package com.example.paisesapp.repository

import com.example.paisesapp.network.ApiResult
import retrofit2.Response
import java.lang.Exception

open class BaseRepository {

    suspend fun <T> safeCallApi(call: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = call.invoke()

            if (response.isSuccessful){
                ApiResult.Success(response.body())
            } else {
                ApiResult.Error(response.message() ?: "Não foi possível fazer a chamada.")
            }
        }catch (error:Exception){
            ApiResult.Error("Não foi possível fazer a chamada.")
        }
    }
}