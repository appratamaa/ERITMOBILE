package org.bumandhala.eritmobile.landingscreennavigation

sealed class Screen(val route: String) {
    data object Home: Screen("landingScreen")
    data object Landing1: Screen("landing1")
}