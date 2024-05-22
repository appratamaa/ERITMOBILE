package org.bumandhala.eritmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.bumandhala.eritmobile.model.Pemasukan
import org.bumandhala.eritmobile.model.Tabungan

@Dao
interface TabunganDao {
    @Insert
    suspend fun insert(tabungan: Tabungan)
    @Update
    suspend fun update(tabungan: Tabungan)
    @Query("SELECT * FROM tabungan ORDER BY id DESC")
    fun getTabungan(): Flow<List<Tabungan>>
    @Query("SELECT * FROM tabungan WHERE id = :id")
    suspend fun getTabunganById(id: Long): Tabungan?
    @Query("DELETE FROM tabungan WHERE id = :id")
    suspend fun deleteTabunganByID(id: Long)
}
