package com.pepivsky.debtorsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pepivsky.debtorsapp.components.Report
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.data.models.entity.DebtorWithMovements
import com.pepivsky.debtorsapp.data.models.entity.Movement
import com.pepivsky.debtorsapp.data.repositories.DebtorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val debtorsRepository: DebtorsRepository,
    private val report: Report,
) :
    ViewModel() {

    private val _showSplash = MutableStateFlow(true)
    val showSplash = _showSplash.asStateFlow()

    private val _allDebtors = MutableStateFlow<List<Debtor>>(emptyList())
    val allDebtors = _allDebtors

    private val _totalAmount: Flow<Double> = debtorsRepository.getTotalAmountOfDebtors()
    val totalAmount = _totalAmount

    private val _selectedDebtorWithMovements = MutableStateFlow<DebtorWithMovements?>(null)
    val selectedDebtorWithMovements = _selectedDebtorWithMovements
    init {
        getAllDebtors()
        //getMovementsSortedByDate()
        //getTotalAmount()
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
                _showSplash.value = false
            }
        }
    }

    // todo mejorar cuando it es null
    /*private fun getTotalAmount() {
        viewModelScope.launch {
            debtorsRepository.getTotalAmountOfDebtors().collect {
                _totalAmount.value = it
            }
        }
    }*/

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

    fun deleteSelectedDebtor(debtor: Debtor) {
        viewModelScope.launch {
            debtorsRepository.deleteDebtor(debtor)
        }
    }

    fun updateDebtor(debtor: Debtor) {
        viewModelScope.launch {
            debtorsRepository.updateDebtor(debtor)
        }
    }

    fun getMovementsSortedByDate() {
        viewModelScope.launch {
            debtorsRepository.getMovementsSortedByDate().collect {
                Log.d("pruebilla", "getMovementsSortedByDate: ${it.joinToString()}")
            }
        }
    }

    fun deleteMovementTransaction(debtor: Debtor, movement: Movement) {
        viewModelScope.launch {
            debtorsRepository.deleteMovement(debtor, movement)
        }

    }


    fun generateAndSharePDF(debtorName: String, movements: List<Movement>, remaining: Double) {
        viewModelScope.launch {
            val pdfUri = report.createPdf(movements, remaining, debtorName)
            pdfUri?.let {
                report.sharePdf(it)
            }
        }
    }


    /*fun deleteDebtorWithMov(debtorWithMovements: DebtorWithMovements) {
        viewModelScope.launch {
            debtorsRepository.deleteDebtorWithMove(debtorWithMovements)
        }
    }*/
}
