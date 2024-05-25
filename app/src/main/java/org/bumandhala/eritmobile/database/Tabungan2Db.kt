package org.bumandhala.eritmobile.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.bumandhala.eritmobile.model.Tabungan2

@Dao
@Database(entities = [Tabungan2::class], version = 1, exportSchema = false)
abstract class Tabungan2Db : RoomDatabase() {
    abstract val dao: Tabungan2Dao
    companion object {
        @Volatile
        private var INSTANCE: Tabungan2Db? = null
        fun getInstance(context: Context): Tabungan2Db {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Tabungan2Db::class.java,
                        "tabungan2.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}