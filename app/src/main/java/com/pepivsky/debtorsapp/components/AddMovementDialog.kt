package com.pepivsky.debtorsapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.pepivsky.debtorsapp.data.models.MovementType
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddMovement(
    movementType: MovementType,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onAcceptClicked: (amount: String, dateText: String) -> Unit,

    ) {
    if (openDialog) {
        Dialog(onDismissRequest = { closeDialog() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(Color.White)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var showDialog by remember { mutableStateOf(false) }
                val state = rememberDatePickerState()
                var dateText by remember { mutableStateOf("${LocalDate.now().dayOfMonth}/${LocalDate.now().monthValue}/${LocalDate.now().year}") }

                //var name by remember { mutableStateOf("") }
                var amount by remember { mutableStateOf("") }
                val isEnable by remember { derivedStateOf { amount.isNotBlank() } }

                //var description by remember { mutableStateOf("") }



                Text(text = if (movementType == MovementType.PAYMENT) "Nuevo Pago" else "Nuevo Aumento", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                /*OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = { Text(text = "Nombre", color = Color(0xFFA1824A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA1824A)

                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    )
                )*/

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = amount,
                    onValueChange = {
                        if (it.isDigitsOnly()) {
                            amount = it
                        }
                    },
                    label = { Text(text = "Monto", color = Color(0xFFA1824A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA1824A)

                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                /*OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = { Text(text = "Descripcion", color = Color(0xFFA1824A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA1824A)

                    ),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )*/
                // Color(0xFF009963)

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showDialog = true }) {
                    Text(text = dateText, color = Color(0xFF009963))
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onAcceptClicked(amount,dateText, )
                        closeDialog()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009963)),
                    enabled = isEnable
                ) {
                    Text(text = "Aceptar")
                }

                if (showDialog) {
                    DatePickerDialog(onDismissRequest = { showDialog = false }, confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text(text = "Confirmar")
                        }
                    }, dismissButton = {
                        OutlinedButton(onClick = { showDialog = false }) {
                            Text(text = "Cancelar")
                        }
                    }) {
                        DatePicker(state = state)
                    }
                }

                val date = state.selectedDateMillis
                date?.let {
                    val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                    dateText = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
                    //Text(text = "Fecha seleccionada: $dateText")
                }

            }
        }
    }


}