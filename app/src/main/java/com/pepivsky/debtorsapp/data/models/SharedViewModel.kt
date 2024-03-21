package com.pepivsky.debtorsapp.data.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pepivsky.debtorsapp.DebtorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val debtorsRepository: DebtorsRepository) :
    ViewModel() {

    private val _allDebtors = MutableStateFlow<List<Debtor>>(emptyList())
    val allDebtors = _allDebtors

    private val _totalAmount = MutableStateFlow<Double>(0.00)
    val totalAmount = _totalAmount

    private val _selectedDebtorWithMovements = MutableStateFlow<DebtorWithMovements?>(null)
    val selectedDebtorWithMovements = _selectedDebtorWithMovements
    init {
        getAllDebtors()
        getTotalAmount()
        //getSelectedDebtorById(1)
    }

    fun getSelectedDebtorWithMovementsById(id: Long) {
        viewModelScope.launch {
            debtorsRepository.getDebtorWithMovementsById(id).collect {
                _selectedDebtorWithMovements.value = it
            }
        }
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

    fun addNewMovement(movement: Movement) {
        viewModelScope.launch {
            debtorsRepository.insertMovement(movement)
        }
    }

    fun addMovementTransaction(debtor: Debtor, movement: Movement) {
        viewModelScope.launch {
            debtorsRepository.addMovementTransaction(debtor, movement)
            //getTotalAmount()
        }
    }
}