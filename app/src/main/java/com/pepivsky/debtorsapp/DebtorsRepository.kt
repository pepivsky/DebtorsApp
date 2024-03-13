package com.pepivsky.debtorsapp

import com.pepivsky.debtorsapp.data.models.Debtor
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class DebtorsRepository @Inject constructor(private val debtorDAO: DebtorDAO) {
    suspend fun addDebtor(debtor: Debtor) {
        debtorDAO.insertDebtor(debtor)
    }
}