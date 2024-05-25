package org.bumandhala.eritmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabungan2")
data class Tabungan2(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val tanggaltabungan: String,
    val namatabungan: String,
    val targettabungan: Int,
    val rencanapengisian: Int,
    val nominalpengisian: Int,
//    val tambahtabungan: Int

)
