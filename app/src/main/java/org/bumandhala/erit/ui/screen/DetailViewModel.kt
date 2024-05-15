package org.bumandhala.erit.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bumandhala.erit.database.CatatanDao
import org.bumandhala.erit.model.Pemasukan
import java.text.SimpleDateFormat
import java.util.Locale

class DetailViewModel(private val dao: CatatanDao): ViewModel() {

    fun insert(tanggal: String, nominal: Int, keterangan: String) {
        val pemasukan = Pemasukan(
            tanggal = tanggal,
            nominal = nominal,
            keterangan = keterangan
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(pemasukan)
        }
    }

    suspend fun getCatatan(id: Long): Pemasukan? {
        return dao.getPemasukanById(id)
    }

    fun update(id: Long, tanggal: String, nominal: Int, keterangan: String) {
        val catatan = Pemasukan(
            id = id,
            tanggal = tanggal,
            nominal = nominal,
            keterangan = keterangan
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(catatan)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteByID(id)
        }
    }
}