package org.bumandhala.eritmobile.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.bumandhala.eritmobile.database.TransaksiDao
import org.bumandhala.eritmobile.model.Pemasukan
import org.bumandhala.eritmobile.model.Pengeluaran

class MainViewModel(dao: TransaksiDao): ViewModel() {
    val pemasukanData: StateFlow<List<Pemasukan>> = dao.getCatatanPemasukan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val pengeluaranData: StateFlow<List<Pengeluaran>> = dao.getCatatanPengeluaran().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}