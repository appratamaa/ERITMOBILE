package org.bumandhala.eritmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.bumandhala.eritmobile.model.Pemasukan
import org.bumandhala.eritmobile.model.Pengeluaran

@Dao
interface TransaksiDao {
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

    @Insert
    suspend fun insertPengeluaran(catatan: Pengeluaran)

    @Update
    suspend fun updatePengeluaran(catatan: Pengeluaran)

    @Query("SELECT * FROM pengeluaran ORDER BY tanggal DESC")
    fun getCatatanPengeluaran(): Flow<List<Pengeluaran>>

    @Query("SELECT * FROM pengeluaran WHERE idPengeluaran = :idPengeluaran")
    suspend fun getPengeluaranById(idPengeluaran: Long): Pengeluaran?

    @Query("DELETE FROM pengeluaran WHERE idPengeluaran = :idPengeluaran")
    suspend fun deletePengeluaranByID(idPengeluaran: Long)

    @Query("SELECT SUM(nominal) FROM pemasukan")
    suspend fun getTotalPemasukan(): Int

    @Query("SELECT SUM(nominal) FROM pengeluaran")
    suspend fun getTotalPengeluaran(): Int
}
