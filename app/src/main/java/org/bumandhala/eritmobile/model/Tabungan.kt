package org.bumandhala.eritmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabungan")
data class Tabungan(
@PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namatabungan: String,
    val targettabungan: Int,
    val rencanapengisian: Int,
    val nominalpengisian: Int

)


