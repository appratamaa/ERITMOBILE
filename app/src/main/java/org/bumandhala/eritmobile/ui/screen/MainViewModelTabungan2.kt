package org.bumandhala.eritmobile.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.bumandhala.eritmobile.database.Tabungan2Dao
import org.bumandhala.eritmobile.model.Tabungan2

class MainViewModelTabungan2(dao: Tabungan2Dao): ViewModel() {
    val data: StateFlow<List<Tabungan2>> = dao.getTabungan2().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}