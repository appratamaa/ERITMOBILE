package org.bumandhala.eritmobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "tabunganscreen")
data class TabunganAll(
//    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namatabungan: String = "",
)