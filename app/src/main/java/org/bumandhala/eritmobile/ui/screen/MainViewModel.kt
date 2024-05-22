package org.bumandhala.eritmobile.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.bumandhala.eritmobile.database.CatatanDao
import org.bumandhala.eritmobile.model.Pemasukan

class MainViewModel(dao: CatatanDao): ViewModel() {
    val data: StateFlow<List<Pemasukan>> = dao.getCatatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}