package com.jkirwa.citiesoftheworldapp.data.remote.api

import com.jkirwa.citiesoftheworldapp.data.remote.model.CitiesResponse
import retrofit2.Response
import retrofit2.http.*

interface CitiesApiService {

    @GET("city")
    suspend fun fetchRemoteCities(
        @Query("page") page: Int
    ): Response<CitiesResponse>

}
