package org.bumandhala.erit.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.bumandhala.erit.database.CatatanDao
import org.bumandhala.erit.ui.screen.DetailViewModel
import org.bumandhala.erit.ui.screen.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val dao: CatatanDao
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