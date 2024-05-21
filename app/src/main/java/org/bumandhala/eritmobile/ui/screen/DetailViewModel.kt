package org.bumandhala.eritmobile.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bumandhala.eritmobile.database.CatatanDao
import org.bumandhala.eritmobile.model.Pemasukan
import org.bumandhala.eritmobile.model.Pengeluaran

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


    //PENGELUARAN
    fun insertPengeluaran(tanggal: String, nominal: Int, keterangan: String) {
        val pemasukan = Pengeluaran(
            tanggal = tanggal,
            nominal = nominal,
            keterangan = keterangan
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertPengeluaran(pemasukan)
        }
    }

    suspend fun getCatatanPengeluaran(idPengeluaran: Long): Pengeluaran? {
        return dao.getPengeluaranById(idPengeluaran)
    }

    fun updatePengeluaran(idPengeluaran: Long, tanggal: String, nominal: Int, keterangan: String) {
        val catatan = Pengeluaran(
            idPengeluaran = idPengeluaran,
            tanggal = tanggal,
            nominal = nominal,
            keterangan = keterangan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.updatePengeluaran(catatan)
        }
    }

    fun deletePengeluaran(idPengeluaran: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deletePengeluaranByID(idPengeluaran)
        }
    }
}