package com.pepivsky.debtorsapp.data.models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pepivsky.debtorsapp.DebtorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val debtorsRepository: DebtorsRepository) : ViewModel() {

    private val _allDebtors = MutableStateFlow<List<Debtor>>(emptyList())
    val allDebtors = _allDebtors

    private val _totalAmount = MutableStateFlow<Double>(0.00)
    val totalAmount = _totalAmount

    init {
        getAllDebtors()
        getTotalAmount()
    }

    private fun getAllDebtors() {
        viewModelScope.launch {
            debtorsRepository.getAllDebtors.collect {
                _allDebtors.value = it
            }
        }
    }

    // todo mejorar cuando it es null
    private fun getTotalAmount() {
        viewModelScope.launch {
            debtorsRepository.getTotalAmountOfDebtors().collect {
                _totalAmount.value = it
            }
        }
    }

     fun addNewDebtor(debtor: Debtor) {
         viewModelScope.launch {
             debtorsRepository.addDebtor(debtor)
         }
    }
}