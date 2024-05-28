package org.bumandhala.eritmobile.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun inserttabungan(namatabungan: String, targettabungan: Int, rencanapengisian: Int, nominalpengisian: Int, tanggaltabungan: String,
//                       rentangwaktu: String, tambahtabungan: Int) {
//        val tabunganscreen = Tabunganscreen(
//            namatabungan = namatabungan,
//            targettabungan = targettabungan,
//            rencanapengisian = rencanapengisian,
//            nominalpengisian = nominalpengisian,
//            tanggaltabungan = tanggaltabungan,
//            rentangwaktu = rentangwaktu,
//            tambahtabungan = tambahtabungan
//        )
//        viewModelScope.launch(Dispatchers.IO) {
//            dao.inserttabungan(tabunganscreen)
//        }
//    }
//
//    suspend fun getTabunganScreen2(id: Long): Tabunganscreen? {
//        return dao.getTabunganScreen2ById(id)
//    }
    fun updatetabungan(id: Long, namatabungan: String, targettabungan: Int, rencanapengisian: Int, nominalpengisian: Int, tanggaltabungan: String,
                       rentangwaktu: String, tambahtabungan: Int) {
        val tabunganscreen = Tabunganscreen(
            id = id,
            namatabungan = namatabungan,
            targettabungan = targettabungan,
            rencanapengisian = rencanapengisian,
            nominalpengisian = nominalpengisian,
            tanggaltabungan = tanggaltabungan,
            rentangwaktu = rentangwaktu,
            tambahtabungan = tambahtabungan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.updatetabungan(tabunganscreen)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteTabunganScreenByID(id)
        }
    }
}