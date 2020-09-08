package com.example.paisesapp.model

import com.example.paisesapp.di.DaggerApiComponent
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CountriesService {

    @Inject
    lateinit var api:CountriesApi

    init {
        DaggerApiComponent.create().inject(this)
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