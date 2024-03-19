package com.pepivsky.debtorsapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pepivsky.debtorsapp.data.models.BigDecimalTypeConverter
import com.pepivsky.debtorsapp.data.models.Debtor
import com.pepivsky.debtorsapp.data.models.Movement

@Database(entities = [Debtor::class, Movement::class], version = 1, exportSchema = false)
@TypeConverters(BigDecimalTypeConverter::class)
abstract class DebtorsDatabase: RoomDatabase() {

    abstract fun getDebtorDAO(): DebtorDAO

    abstract fun getMovementDAO(): MovementDAO
}