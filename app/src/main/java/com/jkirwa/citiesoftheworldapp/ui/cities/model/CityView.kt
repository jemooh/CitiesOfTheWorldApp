package com.jkirwa.citiesoftheworldapp.ui.cities.model

data class CityView(
    val cityId: Int,
    val cityName: String?,
    val countryId: Int?,
    val countryName: String?,
    val countryCode: String?,
    val lat: Double?,
    val lng: Double?,
    val createdAt: String?,
    val updatedAt: String?
)