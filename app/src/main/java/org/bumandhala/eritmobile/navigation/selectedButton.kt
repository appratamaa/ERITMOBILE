package org.bumandhala.eritmobile.navigation

sealed class SelectedButton {
    object Pemasukan : SelectedButton()
    object Pengeluaran : SelectedButton()
    object Tabungan : SelectedButton()
}
