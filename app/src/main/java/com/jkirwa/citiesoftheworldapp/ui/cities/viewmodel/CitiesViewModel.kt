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
import timber.log.Timber

const val PAGE_SIZE = 15

class CitiesViewModel(private val citiesRepository: CitiesRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(CitiesState())
    val state: StateFlow<CitiesState> = _state

    var citiesListScrollPosition = 0

    private var getCitiesJob: Job? = null
    private var searchRemoteCitiesJob: Job? = null
    private var fetchRemoteCitiesJob: Job? = null


    fun getCurrentPage() {
        viewModelScope.launch {
            citiesRepository.getCurrentPage()
                ?.onEach { page_ ->
                    _state.value = state.value.copy(
                        page = page_ ?: 1
                    )
                }?.launchIn(viewModelScope)
        }
    }

    fun fetchRemoteCities() {
        fetchRemoteCitiesJob?.cancel()
        fetchRemoteCitiesJob = viewModelScope.launch(Dispatchers.IO) {
            when (val result = citiesRepository.fetchRemoteCities(1)) {
                is Result.Loading -> {
                    _state.value = state.value.copy(
                        isLoadingCities = true
                    )
                }
                is Result.Success -> {
                    _state.value = state.value.copy(
                        isSuccessFetchingCities = true, isLoadingCities = false
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

    fun searchRemoteCities(query: String) {
        _state.value.isSearchingCities = true
        searchRemoteCitiesJob?.cancel()
        searchRemoteCitiesJob = viewModelScope.launch(Dispatchers.IO) {
            when (val result = citiesRepository.searchRemoteCities(queryText = query, 1)) {
                is Result.Loading -> {
                    _state.value = state.value.copy(
                        isSearchingCities = true
                    )
                }
                is Result.Success -> {
                    _state.value = state.value.copy(
                        isSearchingCities = false
                    )

                }
                is Result.Error -> {
                    _state.value = state.value.copy(
                        isErrorFetchingCities = true, isSearchingCities = false,
                        errorMessage = result.exception.message.toString()
                    )
                }
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            if ((citiesListScrollPosition + 1) >= (state.value.page * PAGE_SIZE)) {
                _state.value = state.value.copy(
                    isLoadingCities = true
                )
                incrementPage()
                Timber.d("nextPage: triggered: ${state.value.page}")

                if (state.value.page > 1) {
                    when (val result = citiesRepository.fetchRemoteCities(state.value.page)) {
                        is Result.Loading -> {
                            _state.value = state.value.copy(
                                isLoadingCities = true
                            )
                        }
                        is Result.Success -> {
                            _state.value = state.value.copy(
                                isSuccessFetchingCities = true, isLoadingCities = false
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
                _state.value = state.value.copy(
                    isLoadingCities = false
                )
            }
        }
    }

    private fun incrementPage() {
        _state.value = state.value.copy(
            page = state.value.page + 1
        )
    }

    fun onChangeCitiesScrollPosition(position: Int) {
        citiesListScrollPosition = position
    }

    private fun resetSearchState() {
        _state.value.cities = emptyList()
        _state.value.isSearchingCities = false
        onChangeCitiesScrollPosition(0)
    }


    fun getAllCities() {
        getCitiesJob?.cancel()
        getCitiesJob = citiesRepository.getCities()
            .onEach { cities_ ->
                if (cities_.isNotEmpty() && !state.value.isSearchingCities && state.value.query.isEmpty()) {
                    _state.value = state.value.copy(
                        cities = cities_
                    )
                } else if (state.value.query.isEmpty() && !state.value.isSearchingCities) {
                    fetchRemoteCities()
                }

            }.launchIn(viewModelScope)
    }

    fun searchCities(query: String) {
        val searchQueryText = "%$query%"
        resetSearchState()
        _state.value.isSearchingCities = true
        citiesRepository.searchCities(searchQueryText)
            .onEach { cities_ ->
                if (cities_.isNotEmpty()) {
                    _state.value = state.value.copy(
                        cities = cities_
                    )
                } else if (query.isNotEmpty() && state.value.isSearchingCities) {
                    searchRemoteCities(query = query)
                }

            }.launchIn(viewModelScope)
    }
}

data class CitiesState(
    var cities: List<CityView> = emptyList(),
    var searchCities: List<CityView> = emptyList(),
    val isSuccessFetchingCities: Boolean = false,
    val isLoadingCities: Boolean = false,
    var isSearchingCities: Boolean = false,
    val errorMessage: String = "",
    var query: String = "",
    val isErrorFetchingCities: Boolean = false,
    var page: Int = 1
)
