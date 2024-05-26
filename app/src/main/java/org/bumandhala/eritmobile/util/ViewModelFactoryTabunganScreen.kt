package org.bumandhala.eritmobile.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.bumandhala.eritmobile.database.TabunganScreenDao
import org.bumandhala.eritmobile.ui.screen.DetailViewModelTabunganScreen
import org.bumandhala.eritmobile.ui.screen.MainViewModelTabunganScreen
import java.lang.IllegalArgumentException

class ViewModelFactoryTabunganScreen(
    private val dao: TabunganScreenDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModelTabunganScreen::class.java)) {
            return MainViewModelTabunganScreen(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModelTabunganScreen::class.java)) {
            return DetailViewModelTabunganScreen(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}