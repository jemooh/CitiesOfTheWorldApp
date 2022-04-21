package com.jkirwa.citiesoftheworldapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @field:SerializedName("data")
    val data: Data? = null
)

data class Data(
    @field:SerializedName("items")
    val items: List<ListItem>? = null
)

data class ListItem(
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("lat")
    val lat: Double? = null,
    @field:SerializedName("lng")
    val lng: Double? = null,
    @field:SerializedName("created_at")
    val createdAt: String? = null,
    @field:SerializedName("updated_at")
    val updatedAt: String? = null,
    @field:SerializedName("name")
    val countryId: Int? = null,
    @field:SerializedName("country")
    val country: Country? = null
)

data class Country(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("created_at")
    val updatedAt: String? = null,

    @field:SerializedName("id")
    val continentId: Int? = null
)
