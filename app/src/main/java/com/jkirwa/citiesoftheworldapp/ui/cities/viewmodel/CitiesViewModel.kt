package com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkirwa.citiesoftheworldapp.data.remote.model.Result
import com.jkirwa.citiesoftheworldapp.data.repository.CitiesRepository
import com.jkirwa.citiesoftheworldapp.ui.cities.model.CityView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CitiesViewModel(private val citiesRepository: CitiesRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(CitiesState())
    val state: StateFlow<CitiesState> = _state

    private var getCitiesJob: Job? = null
    fun fetchRemoteCities() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = citiesRepository.fetchRemoteCities(1)) {
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

    fun getCurrentWeather() {
        getCitiesJob?.cancel()
        getCitiesJob = citiesRepository.getCities()
            .onEach { cities_ ->
                _state.value = state.value.copy(
                    cities = cities_
                )
            }.launchIn(viewModelScope)
    }
}

data class CitiesState(
    val cities: List<CityView> = emptyList(),
    val isSuccessFetchingCities: Boolean = false,
    val isRefreshingCities: Boolean = false,
    val errorMessage: String = "",
    val isErrorFetchingCities: Boolean = false
)
