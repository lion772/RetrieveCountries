package com.example.paisesapp.di

import com.example.paisesapp.BuildConfig
import com.example.paisesapp.countryusecase.CountryUseCase
import com.example.paisesapp.network.AuthInterceptor
import com.example.paisesapp.network.CountriesApi
import com.example.paisesapp.repository.CountryRepository
import com.example.paisesapp.view.MainActivity
import com.example.paisesapp.viewmodel.ListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Module {
    val moduleDI = module{
        single { CountryRepository(get(),get()) }
        single { CountryUseCase(get()) }
        single { MainActivity() }

        viewModel { ListViewModel(countryUseCase = get()) }

    }

    val networkModule = module {
        factory { AuthInterceptor() }
        factory { provideOkHttpClient(get()) }
        factory { provideCountriesApi(get()) }
        single { provideRetrofit(get()) }
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BuildConfig.HOST)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun provideCountriesApi(retrofit: Retrofit) = retrofit.create(CountriesApi::class.java)

    private fun provideOkHttpClient(authInterceptor : AuthInterceptor) =
        OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()


}