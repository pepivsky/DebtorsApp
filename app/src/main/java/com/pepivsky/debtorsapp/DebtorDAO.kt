package com.pepivsky.debtorsapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.pepivsky.debtorsapp.data.models.Debtor
import com.pepivsky.debtorsapp.data.models.DebtorWithMovements
import com.pepivsky.debtorsapp.data.models.Movement
import com.pepivsky.debtorsapp.util.Constants

@Dao
interface DebtorDAO {

    @Insert
    fun insertDebtor(vararg debtor: Debtor)

    @Delete
    fun deleteDebtor(debtor: Debtor)

    @Update
    fun updateDebtor(debtor: Debtor)
    @Query("SELECT * FROM ${Constants.DEBTOR_TABLE}")
    fun getAllDebtors(): List<Debtor>

    @Transaction
    @Query("SELECT * FROM ${Constants.DEBTOR_TABLE}")
    fun getDebtorSWithMovements(): List<DebtorWithMovements>

    @Transaction
    @Query("SELECT * FROM movement WHERE debtorCreatorId =:debtorId")
    fun getMovementsByDebtor(debtorId: Long): List<Movement>
}