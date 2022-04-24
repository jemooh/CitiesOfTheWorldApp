package com.jkirwa.citiesoftheworldapp.di

import androidx.room.Room
import com.jkirwa.citiesoftheworldapp.data.local.datasource.CitiesDatabase
import com.jkirwa.citiesoftheworldapp.data.remote.api.CitiesApiService
import com.jkirwa.citiesoftheworldapp.data.repository.CitiesRepository
import com.jkirwa.citiesoftheworldapp.data.repository.CitiesRepositoryImpl
import com.jkirwa.citiesoftheworldapp.ui.cities.viewmodel.CitiesViewModel
import com.jkirwa.citiesoftheworldapp.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

const val baseUrl: String = Constants.BASE_URL

val appModule = module {
    single { createNetworkClient(baseUrl) }
    single { (get() as? Retrofit)?.create(CitiesApiService::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            CitiesDatabase::class.java,
            CitiesDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single {
        get<CitiesDatabase>().citiesDao
    }

    single {
        get<CitiesDatabase>().countriesDao
    }

    factory<CitiesRepository> {
        CitiesRepositoryImpl(
            citiesApiService = get(),
            citiesDao = get(),
            countriesDao = get(),
        )
    }

    viewModel {
        CitiesViewModel(citiesRepository = get())
    }

}
