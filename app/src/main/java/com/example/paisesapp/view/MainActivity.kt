package com.example.paisesapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paisesapp.R
import com.example.paisesapp.databinding.ActivityMainBinding
import com.example.paisesapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: ListViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.refresh()

        settingCountryAdapter()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        settingCountryAdapter()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries ->
            countries?.let {
                countries_list.visibility = View.VISIBLE
                (countries_list.adapter as CountryListAdapter).updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer {ErrorMessage ->
            ErrorMessage?.let {Message ->
                list_error.text = Message
                //if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it){
                    list_error.visibility = View.GONE
                    countries_list.visibility = View.GONE
                }
            }
        })
    }

    private fun settingCountryAdapter(){
        countries_list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CountryListAdapter()
        }
        (countries_list.adapter as CountryListAdapter).onClickCountry = {Country ->
            Toast.makeText(this, "O país ${Country.countryName} foi clicado!", Toast.LENGTH_SHORT).show()
        }
    }
    /*private fun subscribe() = bind(listViewModel.countries, ::getCountries)

    private fun getCountries(countries:Country) {
        this.countries = countries
    }*/
}