package org.bumandhala.eritmobile.navigation

import org.bumandhala.eritmobile.ui.screen.KEY_ID_CATATAN

sealed class Screen(val route: String) {
    data object LandingScreen: Screen("landingScreen")
    data object Landing1: Screen("landing1")
    data object Landing2: Screen("landing2")
    data object Landing3: Screen("landing3")
    data object Register: Screen("register")
    data object Login: Screen("login")
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_CATATAN}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
    data object Tabungan: Screen("tabungan")
    data object FormBaruTabungan: Screen("detailtabungan")
}