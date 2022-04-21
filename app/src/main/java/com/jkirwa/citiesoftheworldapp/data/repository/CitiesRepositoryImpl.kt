package com.jkirwa.citiesoftheworldapp.data.repository

import com.jkirwa.citiesoftheworldapp.data.local.dao.CitiesDao
import com.jkirwa.citiesoftheworldapp.data.local.model.City
import com.jkirwa.citiesoftheworldapp.data.remote.api.CitiesApiService
import java.io.IOException
import java.util.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import com.jkirwa.citiesoftheworldapp.data.remote.model.Result

internal class CitiesRepositoryImpl(
    private val citiesApiService: CitiesApiService,
    private val citiesDao: CitiesDao,
    private val isDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CitiesRepository {
    override suspend fun fetchRemoteCities(page: Int): Result<Boolean> {
        return withContext(isDispatcher) {
            try {
                Result.Loading
                val result = citiesApiService.fetchRemoteCities(page = page)
                if (result.isSuccessful) {
                    val remoteCities = result.body()
                    remoteCities?.data?.items?.forEach { listItem->
                        Timber.d("RemoteCities"+listItem.name)
                    }

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


    override fun getCities(): Flow<City> {
        return citiesDao.getAllCities()
    }
}
