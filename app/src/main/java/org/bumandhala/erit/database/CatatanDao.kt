package org.bumandhala.erit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.bumandhala.erit.model.Pemasukan

@Dao
interface CatatanDao {
    @Insert
    suspend fun insert(catatan: Pemasukan)
    @Update
    suspend fun update(catatan: Pemasukan)
    @Query("SELECT * FROM pemasukan ORDER BY tanggal DESC")
    fun getCatatan(): Flow<List<Pemasukan>>
    @Query("SELECT * FROM pemasukan WHERE id = :id")
    suspend fun getPemasukanById(id: Long): Pemasukan?
    @Query("DELETE FROM pemasukan WHERE id = :id")
    suspend fun deleteByID(id: Long)
}
