package com.pepivsky.debtorsapp

import com.pepivsky.debtorsapp.data.models.Debtor
import com.pepivsky.debtorsapp.data.models.DebtorWithMovements
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@ViewModelScoped
class DebtorsRepository @Inject constructor(private val debtorDAO: DebtorDAO) {

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


}