package com.jkirwa.citiesoftheworldapp.ui.cities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jkirwa.citiesoftheworldapp.R
import com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel.CitiesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val citiesViewModel: CitiesViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        citiesViewModel.fetchRemoteCities()
    }
}