package com.pepivsky.debtorsapp.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pepivsky.debtorsapp.util.Constants
import java.math.BigDecimal

@Entity(tableName = Constants.DEBTOR_TABLE)
data class Debtor(
    @PrimaryKey(autoGenerate = true)
    val debtorId: Long = 0,
    val name: String,
    val description: String,
    val creationDate: String,
    val amount: Double,
    val remaining: Double,
)
