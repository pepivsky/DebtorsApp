package com.pepivsky.debtorsapp.data.models

import androidx.lifecycle.ViewModel
import com.pepivsky.debtorsapp.DebtorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val debtorsRepository: DebtorsRepository) : ViewModel() {

    suspend fun addNewDebtor(debtor: Debtor) {
        debtorsRepository.addDebtor(debtor)
    }
}