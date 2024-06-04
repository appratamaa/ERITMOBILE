package org.bumandhala.eritmobile.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.bumandhala.eritmobile.database.TransaksiDao
import org.bumandhala.eritmobile.ui.screen.DetailViewModel
import org.bumandhala.eritmobile.ui.screen.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val dao: TransaksiDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}