package org.bumandhala.eritmobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.bumandhala.eritmobile.model.Tabunganscreen

@Dao
interface TabunganScreenDao {
    @Insert
    suspend fun insert(tabunganscreen: Tabunganscreen)
//    @Insert
//    suspend fun inserttabungan(tabunganscreen: Tabunganscreen)
    @Update
    suspend fun update(tabunganscreen: Tabunganscreen)
    @Update
    suspend fun updatetabungan(tabunganscreen: Tabunganscreen)
    @Query("SELECT * FROM tabunganscreen ORDER BY id DESC")
    fun getTabunganScreen(): Flow<List<Tabunganscreen>>
//    @Query("SELECT * FROM tabunganscreen ORDER BY id DESC")
//    fun getTabunganScreen2(): Flow<List<Tabunganscreen>>
    @Query("SELECT * FROM tabunganscreen WHERE id = :id")
    suspend fun getTabunganScreenById(id: Long): Tabunganscreen?
//    @Query("SELECT * FROM tabunganscreen WHERE id = :id")
//    suspend fun getTabunganScreen2ById(id: Long): Tabunganscreen?
    @Query("SELECT * FROM tabunganscreen WHERE id = :id")
    suspend fun getTabungan2ScreenById(id: Long): Tabunganscreen?
    @Query("DELETE FROM tabunganscreen WHERE id = :id")
    suspend fun deleteTabunganScreenByID(id: Long)
}
