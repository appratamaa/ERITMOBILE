package org.bumandhala.eritmobile.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.bumandhala.eritmobile.database.Tabungan2Dao
import org.bumandhala.eritmobile.ui.screen.DetailViewModelTabungan2
import org.bumandhala.eritmobile.ui.screen.MainViewModelTabungan2
import java.lang.IllegalArgumentException

class ViewModelFactoryTabungan2(
    private val dao: Tabungan2Dao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModelTabungan2::class.java)) {
            return MainViewModelTabungan2(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModelTabungan2::class.java)) {
            return DetailViewModelTabungan2(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}