package com.jkirwa.citiesoftheworldapp.utils

import android.Manifest

object Constants {
    const val BASE_URL = "http://connect-demo.mobile1.io/square1/connect/v1/"
    const val PREFS_LATITUDE = "LATITUDE"
    const val PREFS_LONGITUDE = "LONGITUDE"
    const val PERMISSION_ALL = 1
    var PERMISSIONS_LOCATION = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}
