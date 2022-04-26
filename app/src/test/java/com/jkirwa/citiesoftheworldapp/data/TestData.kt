package com.jkirwa.citiesoftheworldapp.data

import com.jkirwa.citiesoftheworldapp.ui.cities.model.CityView
import com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel.CitiesState

internal val testFetchCitiesResponse =
    CitiesState(
        isSuccessFetchingCities = true, isSearchingCities = false
    )

internal val testCitiesRemoteSearchResponse =
    CitiesState(
        isSuccessFetchingCities = true, isSearchingCities = false
    )


val citiesList = (0..5).map {
    CityView(
        184736,
        "Rotterdam",
        122,
        "Netherlands",
        "NT",
        -1.3188,
        36.8511,
        "",
        "",
    )
}
