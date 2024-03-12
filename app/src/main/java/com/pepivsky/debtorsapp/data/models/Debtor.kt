package com.pepivsky.debtorsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pepivsky.debtorsapp.util.Constants

@Entity(tableName = Constants.DEBTOR_TABLE)
data class Debtor(
    @PrimaryKey(autoGenerate = true)
    val debtorId: Long = 0,
    val name: String,
    val description: String,
    val creationDate: String,
    val amount: String,
    val remaining: String,
)
