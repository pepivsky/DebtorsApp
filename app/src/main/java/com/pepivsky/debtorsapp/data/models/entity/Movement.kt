package com.pepivsky.debtorsapp.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pepivsky.debtorsapp.data.models.MovementType
import com.pepivsky.debtorsapp.util.Constants
import java.time.LocalDate

@Entity(
    tableName = Constants.MOVEMENT_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = Debtor::class,
            parentColumns = ["debtorId"],
            childColumns = ["debtorCreatorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("debtorCreatorId")] // Agregamos este índice
)
data class Movement(
    @PrimaryKey(autoGenerate = true)
    val movementId: Long = 0,
    val debtorCreatorId: Long,
    val type: MovementType,
    val amount: Double,
    val date: LocalDate,
    @ColumnInfo(defaultValue = "")
    val concept: String = ""
)
