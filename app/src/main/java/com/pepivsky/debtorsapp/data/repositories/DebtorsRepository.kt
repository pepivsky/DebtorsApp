package com.pepivsky.debtorsapp.data.repositories

import com.pepivsky.debtorsapp.data.dao.DebtorDAO
import com.pepivsky.debtorsapp.data.dao.MovementDAO
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.data.models.entity.DebtorWithMovements
import com.pepivsky.debtorsapp.data.models.entity.Movement
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@ViewModelScoped
class DebtorsRepository @Inject constructor(private val debtorDAO: DebtorDAO, private val movementDAO: MovementDAO) {

    val getAllDebtorsWithMovements: Flow<List<DebtorWithMovements>> =
        debtorDAO.getDebtorSWithMovements()

    val getAllDebtors: Flow<List<Debtor>> = debtorDAO.getAllDebtors()

    suspend fun addDebtor(debtor: Debtor) {
        debtorDAO.insertDebtor(debtor)
    }

    suspend fun deleteDebtor(debtor: Debtor) {
        debtorDAO.deleteDebtor(debtor = debtor)
    }

    suspend fun updateDebtor(debtor: Debtor) {
        debtorDAO.updateDebtor(debtor = debtor)
    }

    suspend fun deleteAllDebtors() {
        debtorDAO.deleteAllDebtors()
    }

    fun getSelectedDebtor(id: Long): Flow<Debtor> {
        return debtorDAO.getSelectedDebtor(id = id)
    }

    fun getTotalAmountOfDebtors(): Flow<Double> {
        return debtorDAO.getTotalAmount().filterNotNull()
    }

    fun getDebtorWithMovementsById(id: Long): Flow<DebtorWithMovements> {
        return debtorDAO.getDebtorWithMovementsById(id)
    }

    suspend fun insertMovement(movement: Movement) {
        movementDAO.insertMovement(movement)
    }

    suspend fun deleteMovement(debtor: Debtor, movement: Movement) {
        debtorDAO.deleteMovementTransaction(debtor, movement)
    }

    suspend fun addMovementTransaction(debtor: Debtor, movement: Movement) {
        debtorDAO.addMovementTransaction(debtor, movement)
    }

    suspend fun getMovementsSortedByDate(): Flow<List<Movement>> {
        return movementDAO.getMovementsSortedByDateASC()
    }


}