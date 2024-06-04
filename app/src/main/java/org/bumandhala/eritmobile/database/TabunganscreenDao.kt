package org.bumandhala.eritmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.bumandhala.eritmobile.model.TabunganAll
import org.bumandhala.eritmobile.model.Tabunganscreen

@Dao
interface TabunganScreenDao {
    @Insert
    suspend fun insert(tabunganscreen: Tabunganscreen)
    @Update
    suspend fun update(tabunganscreen: Tabunganscreen)
//    @Query("SELECT DISTINCT namatabungan FROM tabunganscreen ORDER BY id DESC")
//    @Query("SELECT MIN(id) AS id, namaTabungan FROM tabunganscreen GROUP BY namaTabungan")
//    fun getTabunganScreen(): Flow<List<Tabunganscreen>>
    @Query("SELECT MIN(id) AS id, namatabungan FROM tabunganscreen GROUP BY namatabungan")
    fun getTabunganScreen(): Flow<List<TabunganAll>>
    @Query("SELECT * FROM tabunganscreen ORDER BY id DESC")
    fun getDetailTabunganScreen(): Flow<List<Tabunganscreen>>
    @Query("SELECT * FROM tabunganscreen WHERE namatabungan = :namatabungan ORDER BY id DESC, tanggaltabungan DESC")
    fun getDetailTabunganScreenByNama(namatabungan: String): Flow<List<Tabunganscreen>>
    @Query("SELECT * FROM tabunganscreen WHERE id = :id")
    suspend fun getTabunganScreenById(id: Long): Tabunganscreen?
    @Query("DELETE FROM tabunganscreen WHERE id = :id")
    suspend fun deleteTabunganScreenByID(id: Long)
    ;
}
