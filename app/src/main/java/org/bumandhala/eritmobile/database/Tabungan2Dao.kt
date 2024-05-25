package org.bumandhala.eritmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.bumandhala.eritmobile.model.Tabungan2

@Dao
interface Tabungan2Dao {
    @Insert
    suspend fun insert(tabungan2: Tabungan2)
    @Update
    suspend fun update(tabungan2: Tabungan2)
    @Query("SELECT * FROM tabungan2 ORDER BY id DESC")
    fun getTabungan2(): Flow<List<Tabungan2>>
    @Query("SELECT * FROM tabungan2 WHERE id = :id")
    suspend fun getTabungan2ById(id: Long): Tabungan2?
    @Query("DELETE FROM tabungan2 WHERE id = :id")
    suspend fun deleteTabungan2ByID(id: Long)
}
