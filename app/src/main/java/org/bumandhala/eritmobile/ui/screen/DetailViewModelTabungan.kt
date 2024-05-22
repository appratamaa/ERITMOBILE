package org.bumandhala.eritmobile.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.database.TabunganDao
import org.bumandhala.eritmobile.model.Tabungan

class DetailViewModelTabungan(private val dao: TabunganDao): ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert(namatabungan: String, targettabungan: Int, rencanapengisian: Int, nominalpengisian: Int) {
        val tabungan = Tabungan(
            namatabungan = namatabungan,
            targettabungan = targettabungan,
            rencanapengisian = rencanapengisian,
            nominalpengisian = nominalpengisian
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(tabungan)
        }
    }

    suspend fun getTabungan(id: Long): Tabungan? {
        return dao.getTabunganById(id)
    }

    fun update(id: Long, namatabungan: String, targettabungan: Int, rencanapengisian: Int, nominalpengisian: Int) {
        val tabungan = Tabungan(
            id = id,
            namatabungan = namatabungan,
            targettabungan = targettabungan,
            rencanapengisian = rencanapengisian,
            nominalpengisian = nominalpengisian
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(tabungan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteTabunganByID(id)
        }
    }
}