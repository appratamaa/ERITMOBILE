package org.bumandhala.eritmobile.landingscreennavigation

sealed class Screen(val route: String) {
    data object LandingScreen: Screen("landingScreen")
    data object Landing1: Screen("landing1")
    data object Landing2: Screen("landing2")
    data object Landing3: Screen("landing3")
    data object Register: Screen("register")
    data object Login: Screen("login")
    data object Home: Screen("mainscreen")
}