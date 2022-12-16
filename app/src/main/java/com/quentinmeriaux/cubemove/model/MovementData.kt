package com.quentinmeriaux.cubemove.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovementData(
    val x: Int,
    val y: Int,
    val date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}