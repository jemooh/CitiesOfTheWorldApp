package com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkirwa.citiesoftheworldapp.data.local.model.City
import com.jkirwa.citiesoftheworldapp.data.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.jkirwa.citiesoftheworldapp.data.remote.model.Result

class CitiesViewModel(private val citiesRepository: CitiesRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(CitiesState())
    val state: StateFlow<CitiesState> = _state


    fun fetchRemoteCities() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = citiesRepository.fetchRemoteCities(20)) {
                is Result.Loading -> {
                    _state.value = state.value.copy(
                        isRefreshingCities = true
                    )
                }
                is Result.Success -> {
                    _state.value = state.value.copy(
                        isSuccessFetchingCities = true
                    )
                }
                is Result.Error -> {
                    _state.value = state.value.copy(
                        isErrorFetchingCities = true,
                        errorMessage = result.exception.message.toString()
                    )
                }
            }
        }
    }
}

data class CitiesState(
    val cities: List<City> = emptyList(),
    val isSuccessFetchingCities: Boolean = false,
    val isRefreshingCities: Boolean = false,
    val errorMessage: String = "",
    val isErrorFetchingCities: Boolean = false
)
