package org.bumandhala.eritmobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.bumandhala.eritmobile.model.Pemasukan
import org.bumandhala.eritmobile.model.Pengeluaran

@Database(entities = [Pemasukan::class, Pengeluaran::class], version = 2, exportSchema = false)
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
                    )
                        .addMigrations(MIGRATION_1_2) // Tambahkan migrasi di sini
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        // Migrasi dari versi 1 ke 2, menambahkan kolom imagePath
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE pengeluaran ADD COLUMN imagePath TEXT")
            }
        }
    }
}
