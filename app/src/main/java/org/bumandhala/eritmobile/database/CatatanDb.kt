package org.bumandhala.eritmobile.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.bumandhala.eritmobile.model.Pemasukan

@Dao
@Database(entities = [Pemasukan::class], version = 1, exportSchema = false)
abstract class CatatanDb : RoomDatabase() {
    abstract val dao: CatatanDao
    companion object {
        @Volatile
        private var INSTANCE: CatatanDb? = null
        fun getInstance(context: Context): CatatanDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CatatanDb::class.java,
                        "catatan.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}