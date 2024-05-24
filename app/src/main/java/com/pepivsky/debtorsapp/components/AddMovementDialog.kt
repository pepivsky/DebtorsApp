package com.pepivsky.debtorsapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pepivsky.debtorsapp.R
import com.pepivsky.debtorsapp.data.models.MovementType
import com.pepivsky.debtorsapp.util.extension.formatToServerDateDefaults
import com.pepivsky.debtorsapp.util.numberValidator
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddMovement(
    movementType: MovementType,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onAcceptClicked: (amount: String, dateText: LocalDate, concept: String) -> Unit,

    ) {
    if (openDialog) {
        Dialog(onDismissRequest = { closeDialog() }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors()

            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var showDialog by remember { mutableStateOf(false) }
                    val state = rememberDatePickerState()
                    var dateText by remember { mutableStateOf(LocalDate.now()) }

                    var amount by remember { mutableStateOf("") }
                    val isEnable by remember { derivedStateOf { amount.isNotBlank() } }
                    var movementConcept by remember { mutableStateOf("") }



                    Text(
                        text = if (movementType == MovementType.PAYMENT) stringResource(R.string.label_new_payment) else stringResource(
                            R.string.label_new_increase
                        ),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 20.sp
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = amount,
                        onValueChange = { str ->
                            if (numberValidator(str)) {
                                amount = str
                            }
                        },
                        label = { Text(text = stringResource(R.string.amount)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = movementConcept,
                        onValueChange = { str ->
                            movementConcept = str
                        },
                        label = { Text(text = stringResource(R.string.concept_label)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done
                        )
                    )

                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { showDialog = true }) {
                        Text(text = dateText.formatToServerDateDefaults())
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onAcceptClicked(amount, dateText, movementConcept)
                            closeDialog()
                        },
                        enabled = isEnable
                    ) {
                        Text(text = stringResource(R.string.accept))
                    }

                    if (showDialog) {
                        DatePickerDialog(
                            onDismissRequest = { showDialog = false },
                            confirmButton = {
                                Button(onClick = { showDialog = false }) {
                                    Text(text = stringResource(R.string.confirm))
                                }
                            },
                            dismissButton = {
                                OutlinedButton(onClick = { showDialog = false }) {
                                    Text(text = stringResource(id = R.string.cancel))
                                }
                            }) {
                            DatePicker(state = state)
                        }
                    }

                    val date = state.selectedDateMillis
                    date?.let {
                        val localDate =
                            Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                        dateText = localDate
                    }
                }
            }
        }
    }
}