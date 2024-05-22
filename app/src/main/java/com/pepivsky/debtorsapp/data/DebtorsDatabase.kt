package com.pepivsky.debtorsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pepivsky.debtorsapp.data.converters.BigDecimalTypeConverter
import com.pepivsky.debtorsapp.data.dao.DebtorDAO
import com.pepivsky.debtorsapp.data.dao.MovementDAO
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.data.models.entity.Movement

@Database(
    entities = [Debtor::class, Movement::class],
    version = 2,
    exportSchema = true,
)
@TypeConverters(BigDecimalTypeConverter::class)
abstract class DebtorsDatabase : RoomDatabase() {

    abstract fun getDebtorDAO(): DebtorDAO

    abstract fun getMovementDAO(): MovementDAO
}