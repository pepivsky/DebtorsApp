package com.pepivsky.debtorsapp.data.models

import androidx.navigation.NavController
import com.pepivsky.debtorsapp.data.models.entity.Debtor

sealed interface UiEvent {
    data class DeleteDebtor(val debtor: Debtor, val navController: NavController): UiEvent
    //data class
}