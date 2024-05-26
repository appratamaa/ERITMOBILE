package org.bumandhala.eritmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabunganscreen")
data class Tabunganscreen(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val tanggaltabungan: String,
    val namatabungan: String,
    val targettabungan: Int,
    val rencanapengisian: Int,
    val nominalpengisian: Int,
    val rentangwaktu: String,
    val tambahtabungan: Int
)