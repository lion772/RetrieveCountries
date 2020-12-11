package com.example.paisesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paisesapp.countryusecase.CountryUseCase
import com.example.paisesapp.model.Country
import com.example.paisesapp.network.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val countryUseCase: CountryUseCase): ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>>
        get() = _countries

    private val _countryLoadError = MutableLiveData<String>()
    val countryLoadError: LiveData<String>
        get() = _countryLoadError

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun refresh(){
        fetchCountries()
    }

    private fun fetchCountries() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = countryUseCase.getCountries()){
                is ApiResult.Success -> _countries.postValue(response.data)
                is ApiResult.Error -> _countryLoadError.postValue(response.message)
            }
            _loading.postValue(false)
        }
    }


    /*init {
        DaggerApiComponent.create().injectCountryService(this)
    }

    fun refresh(){
        fetchCountries()
    }

    private fun fetchCountries(){
        _loading.value = true
        disposable.add(
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(value: List<Country>?) {
                        _countries.value = value
                        _countryLoadError.value = false
                        _loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        _countryLoadError.value = true
                        _loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }*/
}