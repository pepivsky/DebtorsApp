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
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtorDAO {

    @Insert
    suspend fun insertDebtor(vararg debtor: Debtor)

    @Delete
    suspend fun deleteDebtor(debtor: Debtor)

    @Update
    suspend fun updateDebtor(debtor: Debtor)

    @Query("DELETE FROM debtor")
    suspend fun deleteAllDebtors()

    @Query("SELECT * FROM ${Constants.DEBTOR_TABLE}")
    fun getAllDebtors(): Flow<List<Debtor>>

    @Query("SELECT * FROM debtor WHERE debtorId = :id")
    fun getSelectedDebtor(id: Long): Flow<Debtor>

    @Transaction
    @Query("SELECT * FROM ${Constants.DEBTOR_TABLE}")
    fun getDebtorSWithMovements(): Flow<List<DebtorWithMovements>>

    @Query("SELECT SUM(amount) FROM ${Constants.DEBTOR_TABLE}")
    fun getTotalAmount(): Flow<Double>

    @Transaction
    @Query("SELECT * FROM ${Constants.DEBTOR_TABLE} WHERE debtorId = :id")
    fun getDebtorWithMovementsById(id: Long): Flow<DebtorWithMovements>


    /*@Transaction
    @Query("SELECT * FROM movement WHERE debtorCreatorId =:debtorId")
    fun getMovementsByDebtor(debtorId: Long): Flow<List<Movement>>

    @Insert
    suspend fun insertMovement(vararg movement: Movement)*/
}