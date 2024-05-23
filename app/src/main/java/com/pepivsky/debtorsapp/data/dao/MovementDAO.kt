package com.pepivsky.debtorsapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pepivsky.debtorsapp.data.models.entity.Movement
import kotlinx.coroutines.flow.Flow

@Dao
interface MovementDAO {

    @Insert
    suspend fun insertMovement(vararg movement: Movement)

    @Delete
    suspend fun deleteMovement(movement: Movement)

    @Update
    suspend fun udpateMovement(movement: Movement)

    @Query("SELECT * FROM movement")
    fun getAllMovements(): Flow<List<Movement>>

    @Query("DELETE FROM movement")
    suspend fun deleteAllMovements()
    @Query("SELECT * FROM movement ORDER BY date ASC")
    fun getMovementsSortedByDateASC(): Flow<List<Movement>>

    @Query("SELECT * FROM movement ORDER BY date DESC")
    fun getMovementsSortedByDateDESC(): Flow<List<Movement>>




    /*@Query("SELECT * FROM movement WHERE debtorCreatorId = :id")
    fun getMovementsByDebtor(id: Long): Flow<List<Movement>>*/
}