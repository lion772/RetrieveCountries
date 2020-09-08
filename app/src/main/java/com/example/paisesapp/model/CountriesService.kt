package com.example.paisesapp.model

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {
    private val api:CountriesApi
    private val BASE_URL = "https://raw.githubusercontent.com"

    init {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }

    fun getCountries(): Single<List<Country>>{
        return api.getCountries()
    }

    /*private lateinit var retrofit: Retrofit
    private fun getRetrofitInstance(): Retrofit{
        val httpClient = OkHttpClient.Builder().build()
        if (!::retrofit.isInitialized){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun <T> createService(serviceClass: Class<T>): T = getRetrofitInstance().create(serviceClass)*/

}