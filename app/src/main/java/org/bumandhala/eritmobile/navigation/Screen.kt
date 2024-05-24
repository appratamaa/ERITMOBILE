package org.bumandhala.eritmobile.navigation

import org.bumandhala.eritmobile.ui.screen.KEY_ID_PEMASUKAN
import org.bumandhala.eritmobile.ui.screen.KEY_ID_PENGELUARAN

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
}
