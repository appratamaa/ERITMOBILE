package org.bumandhala.eritmobile.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.bumandhala.eritmobile.model.Tabunganscreen

@Dao
@Database(entities = [Tabunganscreen::class], version = 1, exportSchema = false)
abstract class TabunganScreenDb : RoomDatabase() {
    abstract val dao: TabunganScreenDao
    companion object {
        @Volatile
        private var INSTANCE: TabunganScreenDb? = null
        fun getInstance(context: Context): TabunganScreenDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TabunganScreenDb::class.java,
                        "tabunganscreen.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}