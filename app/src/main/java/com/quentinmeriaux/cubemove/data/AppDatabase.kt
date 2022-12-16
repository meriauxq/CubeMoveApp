package com.quentinmeriaux.cubemove.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quentinmeriaux.cubemove.model.MovementData

@Database(entities = [MovementData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movementDataDao(): MovementDataDao
}
