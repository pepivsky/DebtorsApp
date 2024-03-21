package com.pepivsky.debtorsapp.data.models.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.data.models.entity.Movement

data class DebtorWithMovements(
    @Embedded
    val debtor: Debtor,
    @Relation(
        parentColumn = "debtorId",
        entityColumn = "debtorCreatorId"
    )
    val movements: List<Movement>
)
