package com.quentinmeriaux.cubemove.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.quentinmeriaux.cubemove.model.MovementData
import kotlinx.coroutines.flow.Flow

@Dao
interface MovementDataDao {
    @Query("SELECT * FROM MovementData")
    fun getAll(): Flow<List<MovementData>>

    @Insert
    suspend fun add(movementData: MovementData)
}