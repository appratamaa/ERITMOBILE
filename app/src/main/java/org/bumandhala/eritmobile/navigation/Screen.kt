package org.bumandhala.eritmobile.navigation

import org.bumandhala.eritmobile.ui.screen.KEY_ID_PEMASUKAN
import org.bumandhala.eritmobile.ui.screen.KEY_ID_PENGELUARAN
import org.bumandhala.eritmobile.ui.screen.KEY_ID_TABUNGAN
import org.bumandhala.eritmobile.ui.screen.KEY_NAMA_TABUNGAN

sealed class Screen(val route: String) {
    object Home : Screen("mainScreen")
    object FormBaruPemasukan : Screen("detailScreenPemasukan")
    object FormBaruPengeluaran : Screen("detailScreenPengeluaran")
    object FormUbahPemasukan : Screen("detailScreenPemasukan/{$KEY_ID_PEMASUKAN}") {
        fun withIdPemasukan(idPemasukan: Long) = "detailScreenPemasukan/$idPemasukan"
    }
    object FormUbahPengeluaran : Screen("detailScreenPengeluaran/{$KEY_ID_PENGELUARAN}") {
        fun withIdPengeluaran(idPengeluaran: Long) = "detailScreenPengeluaran/$idPengeluaran"
    }


    data object LandingScreen: Screen("landingScreen")
    data object Landing1: Screen("landing1")
    data object Landing2: Screen("landing2")
    data object Landing3: Screen("landing3")
    data object Register: Screen("register")
    data object Login: Screen("login")
    data object FormBaru: Screen("detailScreen")
    data object Tabungan: Screen("tabungan")
    data object FormBaruTabungan: Screen("detailtabungan")
    data object DetailTabunganTabungan: Screen("detailtabungantabungan/{$KEY_ID_TABUNGAN}") {
        fun withId(id: Long) = "detailtabungantabungan/$id"
    }
    data object TambahtabunganBaru: Screen("tambahtabungan")
    data object Tambahtabungan: Screen("tambahtabungan/{$KEY_NAMA_TABUNGAN}") {
        fun withNama(namatabungan: String) = "tambahtabungan/$namatabungan"
    }

    data object Profile: Screen("profile")

    companion object {
        const val routeWithArgument = "profile/{userId}"
        const val KEY_USER_ID = "userId"
    }

    data object ResetPassword: Screen("resetpassword")
}
