package com.pepivsky.debtorsapp.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class DebtorWithMovements(
    @Embedded
    val debtor: Debtor,
    @Relation(
        parentColumn = "debtorId",
        entityColumn = "debtorCreatorId"
    )
    val movements: List<Movement>
)
