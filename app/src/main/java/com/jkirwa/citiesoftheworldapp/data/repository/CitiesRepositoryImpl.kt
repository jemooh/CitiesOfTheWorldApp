package com.jkirwa.citiesoftheworldapp.data.repository

import com.jkirwa.citiesoftheworldapp.data.local.dao.CitiesDao
import com.jkirwa.citiesoftheworldapp.data.local.dao.CountriesDao
import com.jkirwa.citiesoftheworldapp.data.local.model.City
import com.jkirwa.citiesoftheworldapp.data.local.model.Country
import com.jkirwa.citiesoftheworldapp.data.remote.api.CitiesApiService
import com.jkirwa.citiesoftheworldapp.data.remote.model.CitiesResponse
import com.jkirwa.citiesoftheworldapp.data.remote.model.Result
import com.jkirwa.citiesoftheworldapp.ui.cities.model.CityView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException

internal class CitiesRepositoryImpl(
    private val citiesApiService: CitiesApiService,
    private val citiesDao: CitiesDao,
    private val countriesDao: CountriesDao,
    private val isDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CitiesRepository {
    override suspend fun fetchRemoteCities(page: Int): Result<Boolean> {
        return withContext(isDispatcher) {
            try {
                Result.Loading
                val result = citiesApiService.fetchRemoteCities(page = page, include = "country")
                if (result.isSuccessful) {
                    val remoteCities = result.body()
                    saveRemoteData(remoteCities = remoteCities)
                    Result.Success(true)
                } else {
                    Result.Success(false)
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (e: IOException) {
                Result.Error(Exception("Error Occurred"))
                e.printStackTrace()
                Timber.e(e)
            }
            Result.Success(false)
        }
    }


    override suspend fun searchRemoteCities(queryText: String, page: Int): Result<Boolean> {
        return withContext(isDispatcher) {
            try {
                Result.Loading
                val result = citiesApiService.searchRemoteCities(
                    queryText = queryText,
                    page = page,
                    include = "country"
                )
                if (result.isSuccessful) {
                    if (result.body()?.data?.items?.isNotEmpty() == true) {
                        val remoteCities = result.body()
                        saveRemoteData(remoteCities = remoteCities, isSearch = true)
                        Result.Success(true)
                    } else {
                        Result.Success(false)
                    }

                } else {
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (e: IOException) {
                Result.Error(Exception("Error Occurred"))
                e.printStackTrace()
                Timber.e(e)
            }
            Result.Success(false)
        }
    }

    private suspend fun saveRemoteData(remoteCities: CitiesResponse?, isSearch: Boolean = false) {
        remoteCities?.data?.items?.forEach { listItem ->
            listItem?.apply {
                val city = City(
                    cityId = id ?: 0,
                    cityName = name,
                    countryId = country?.id,
                    lat = lat,
                    lng = lng,
                    createdAt = createdAt,
                    updatedAt = updatedAt
                )

                if (!isSearch) {
                    city.currentPage = remoteCities?.data?.pagination?.currentPage ?: 1
                }
                citiesDao.insertAsync(city)
                country?.apply {
                    val country = Country(
                        countryId = id ?: 0,
                        countryName = name,
                        countryCode = code,
                        createdAt = createdAt,
                        updatedAt = updatedAt,
                        continentId = continentId
                    )
                    countriesDao.insertAsync(country)
                }

            }

        }
    }


    override fun getCities(): Flow<List<CityView>> {
        return citiesDao.getAllCities()
    }

    override fun getCurrentPage(): Flow<Int?>? {
        return citiesDao.getCurrentPage()
    }

    override fun searchCities(queryText: String): Flow<List<CityView>> {
        return citiesDao.searchCities(queryText = queryText)
    }
}
