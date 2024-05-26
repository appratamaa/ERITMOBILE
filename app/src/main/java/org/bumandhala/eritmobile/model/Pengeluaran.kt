package org.bumandhala.eritmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengeluaran")
data class Pengeluaran(
    @PrimaryKey(autoGenerate = true)
    val idPengeluaran: Long = 0L,
    val tanggal: String,
    val nominal: Int,
    val keterangan: String,
    val imagePath: String? // Tambahkan properti baru
)
