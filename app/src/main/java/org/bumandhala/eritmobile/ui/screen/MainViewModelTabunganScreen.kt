package org.bumandhala.eritmobile.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.bumandhala.eritmobile.database.TabunganScreenDao
import org.bumandhala.eritmobile.model.TabunganAll
import org.bumandhala.eritmobile.model.Tabunganscreen

class MainViewModelTabunganScreen(dao: TabunganScreenDao): ViewModel() {


    val data: StateFlow<List<TabunganAll>> = dao.getTabunganScreen().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    val detail: StateFlow<List<Tabunganscreen>> = dao.getDetailTabunganScreen().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}