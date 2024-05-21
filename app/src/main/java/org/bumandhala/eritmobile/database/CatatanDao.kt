package org.bumandhala.eritmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.bumandhala.eritmobile.model.Pemasukan

@Dao
interface CatatanDao {
    @Insert
    suspend fun insertPemasukan(catatan: Pemasukan)
    @Update
    suspend fun updatePemasukan(catatan: Pemasukan)
    @Query("SELECT * FROM pemasukan ORDER BY tanggal DESC")
    fun getCatatanPemasukan(): Flow<List<Pemasukan>>
    @Query("SELECT * FROM pemasukan WHERE idPemasukan = :idPemasukan")
    suspend fun getPemasukanById(idPemasukan: Long): Pemasukan?
    @Query("DELETE FROM pemasukan WHERE idPemasukan = :idPemasukan")
    suspend fun deletePemasukanByID(idPemasukan: Long)
}
