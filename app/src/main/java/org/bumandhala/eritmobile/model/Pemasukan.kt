package org.bumandhala.eritmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pemasukan")
data class Pemasukan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val tanggal: String,
    val nominal: Int,
    val keterangan: String
)