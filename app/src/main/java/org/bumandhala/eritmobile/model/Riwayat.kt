package org.bumandhala.eritmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "riwayat")
data class Riwayat(
    @PrimaryKey(autoGenerate = true)
    val idPemasukan: Long = 0L,
    val tanggal: String,
    val nominal: Int,
    val keterangan: String
)