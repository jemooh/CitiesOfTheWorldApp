package com.jkirwa.citiesoftheworldapp.ui.cities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jkirwa.citiesoftheworldapp.R
import com.jkirwa.citiesoftheworldapp.ui.cities.composable.BottomNavigationBar
import com.jkirwa.citiesoftheworldapp.ui.cities.composable.Navigation
import com.jkirwa.citiesoftheworldapp.ui.cities.theme.CitiesAppTheme
import com.jkirwa.citiesoftheworldapp.ui.cities.theme.TopBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent {
            MainScreen()
        }
    }


    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        CitiesAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavigationBar(navController) }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }
}