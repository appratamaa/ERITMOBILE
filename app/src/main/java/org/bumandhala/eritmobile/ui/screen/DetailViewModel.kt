package org.bumandhala.eritmobile.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.database.CatatanDao
import org.bumandhala.eritmobile.model.Pemasukan

class DetailViewModel(private val dao: CatatanDao): ViewModel() {

    fun insertPemasukan(tanggal: String, nominal: Int, keterangan: String) {
        val pemasukan = Pemasukan(
            tanggal = tanggal,
            nominal = nominal,
            keterangan = keterangan
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertPemasukan(pemasukan)
        }
    }

    suspend fun getCatatanPemasukan(idPemasukan: Long): Pemasukan? {
        return dao.getPemasukanById(idPemasukan)
    }

    fun updatePemasukan(idPemasukan: Long, tanggal: String, nominal: Int, keterangan: String) {
        val catatan = Pemasukan(
            idPemasukan = idPemasukan,
            tanggal = tanggal,
            nominal = nominal,
            keterangan = keterangan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.updatePemasukan(catatan)
        }
    }

    fun deletePemasukan(idPemasukan: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deletePemasukanByID(idPemasukan)
        }
    }
}