package org.bumandhala.eritmobile.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.database.Tabungan2Dao
import org.bumandhala.eritmobile.model.Tabungan2

class DetailViewModelTabungan2(private val dao: Tabungan2Dao): ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert(namatabungan: String, targettabungan: Int, rencanapengisian: Int, nominalpengisian: Int, tanggaltabungan: String,
               tambahtabungan: Int) {
        val tabungan2 = Tabungan2(
            tanggaltabungan = tanggaltabungan,
            namatabungan = namatabungan,
            targettabungan = targettabungan,
            rencanapengisian = rencanapengisian,
            nominalpengisian = nominalpengisian,
//            tambahtabungan = tambahtabungan
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(tabungan2)
        }
    }

    suspend fun getTabungan2(id: Long): Tabungan2? {
        return dao.getTabungan2ById(id)
    }

//    fun update(id: Long, namatabungan: String, targettabungan: Int, rencanapengisian: Int, nominalpengisian: Int) {
//        val tabungan = Tabungan(
//            id = id,
//            namatabungan = namatabungan,
//            targettabungan = targettabungan,
//            rencanapengisian = rencanapengisian,
//            nominalpengisian = nominalpengisian,
//        )
//
//        viewModelScope.launch(Dispatchers.IO) {
//            dao.update(tabungan)
//        }
//    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteTabungan2ByID(id)
        }
    }
}