package org.bumandhala.eritmobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.bumandhala.eritmobile.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class EritDb: RoomDatabase() {
    abstract val dao: UserDao
    companion object {
        @Volatile
        private var INSTANCE: EritDb? = null

        fun getInstance(context: Context): EritDb {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EritDb::class.java,
                        "erit.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}