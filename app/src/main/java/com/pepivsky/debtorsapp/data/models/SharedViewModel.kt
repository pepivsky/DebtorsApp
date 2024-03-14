package com.pepivsky.debtorsapp.data.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pepivsky.debtorsapp.DebtorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val debtorsRepository: DebtorsRepository) : ViewModel() {

     fun addNewDebtor(debtor: Debtor) {
         viewModelScope.launch {
             debtorsRepository.addDebtor(debtor)
         }
    }
}