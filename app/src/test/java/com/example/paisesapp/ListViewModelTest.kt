package com.example.paisesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.paisesapp.model.CountriesService
import com.example.paisesapp.model.Country
import com.example.paisesapp.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.time.Instant
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle:Single<List<Country>>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSuccess(){
        val countriesList: ArrayList<Country> = arrayListOf(Country(
            "countryName", "capital", "flag"))

        testSingle = Single.just(countriesList)
        `when`(countriesService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()
        assertEquals(1, listViewModel.countries.value?.size)
        assertEquals(false, listViewModel.countryLoadError.value)
        assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getCountryFail(){
        testSingle = Single.error(Throwable())
        `when`(countriesService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()
        assertEquals(null, listViewModel.countries.value?.size)
        assertEquals(true, listViewModel.countryLoadError.value)
        assertEquals(false, listViewModel.loading.value)

    }

    @Before
    fun setUpRxSchedulers(){
        val immediate = object: Scheduler(){
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler: Callable<Scheduler>? -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler>? -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler>? -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler>? -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler>? -> immediate }
    }
}