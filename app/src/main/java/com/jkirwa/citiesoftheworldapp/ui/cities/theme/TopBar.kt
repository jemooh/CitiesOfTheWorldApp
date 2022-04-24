package com.jkirwa.citiesoftheworldapp.ui.cities.theme

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopBar() {

    TopAppBar(
        title = { Text("Cities Of The World App") }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    CitiesAppTheme {
        TopBar()
    }
}
