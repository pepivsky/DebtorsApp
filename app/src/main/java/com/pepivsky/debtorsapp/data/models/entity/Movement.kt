package com.pepivsky.debtorsapp.data.models.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.pepivsky.debtorsapp.data.models.MovementType
import com.pepivsky.debtorsapp.util.Constants
import java.math.BigDecimal

@Entity(Constants.MOVEMENT_TABLE,
    foreignKeys = [ForeignKey(
        entity = Debtor::class,
        parentColumns = ["debtorId"],
        childColumns = ["debtorCreatorId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Movement(
    @PrimaryKey(autoGenerate = true)
    val movementId: Long = 0,
    val debtorCreatorId: Long,
    val type: MovementType,
    val amount: Double,
    val date: String
)
