package org.bumandhala.eritmobile.navigation

import org.bumandhala.eritmobile.ui.screen.KEY_ID_PEMASUKAN
import org.bumandhala.eritmobile.ui.screen.KEY_ID_PENGELUARAN

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaruPemasukan: Screen("detailScreenPemasukan")
    data object FormBaruPengeluaran: Screen("detailScreenPengeluaran")
    data object FormUbahPemasukan: Screen("detailScreen/{$KEY_ID_PEMASUKAN}") {
        fun withIdPemasukan(idPemasukan: Long) = "detailScreen/$idPemasukan"
    }
    data object FormUbahPengeluaran: Screen("detailScreen/{$KEY_ID_PENGELUARAN}") {
        fun withIdPengeluaran(idPengeluaran: Long) = "detailScreen/$idPengeluaran"
    }
}
