package org.bumandhala.eritmobile.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.bumandhala.eritmobile.database.CatatanDao
import org.bumandhala.eritmobile.database.TabunganDao
import org.bumandhala.eritmobile.ui.screen.DetailViewModel
import org.bumandhala.eritmobile.ui.screen.DetailViewModelTabungan
import org.bumandhala.eritmobile.ui.screen.MainViewModel
import org.bumandhala.eritmobile.ui.screen.MainViewModelTabungan
import java.lang.IllegalArgumentException

class ViewModelFactoryTabungan(
    private val dao: TabunganDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModelTabungan::class.java)) {
            return MainViewModelTabungan(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModelTabungan::class.java)) {
            return DetailViewModelTabungan(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}