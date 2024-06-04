package org.bumandhala.eritmobile.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.database.TabunganScreenDao
import org.bumandhala.eritmobile.model.Tabunganscreen

class DetailViewModelTabunganScreen(private val dao: TabunganScreenDao): ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert(namatabungan: String, targettabungan: Int, rencanapengisian: Int, nominalpengisian: Int, tanggaltabungan: String,
               rentangwaktu: String, tambahtabungan: Int) {
        val tabunganscreen = Tabunganscreen(
            namatabungan = namatabungan,
            targettabungan = targettabungan,
            rencanapengisian = rencanapengisian,
            nominalpengisian = nominalpengisian,
            tanggaltabungan = tanggaltabungan,
            rentangwaktu = rentangwaktu,
            tambahtabungan = tambahtabungan
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(tabunganscreen)
        }
    }

    suspend fun getTabunganScreen(id: Long): Tabunganscreen? {
        return dao.getTabunganScreenById(id)
    }

    suspend fun getDetailTabunganScreenByNama(namatabungan: String): Flow<List<Tabunganscreen>> {
        return dao.getDetailTabunganScreenByNama(namatabungan)
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
            dao.deleteTabunganScreenByID(id)
        }
    }
}