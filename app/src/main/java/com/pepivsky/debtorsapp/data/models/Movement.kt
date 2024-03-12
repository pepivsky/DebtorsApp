package com.pepivsky.debtorsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pepivsky.debtorsapp.util.Constants

@Entity(Constants.MOVEMENT_TABLE)
data class Movement(
    @PrimaryKey(autoGenerate = true)
    val movementId: Long = 0,
    val debtorCreatorId: Long,
    val type: String,
    val amount: String,
    val date: String
)
