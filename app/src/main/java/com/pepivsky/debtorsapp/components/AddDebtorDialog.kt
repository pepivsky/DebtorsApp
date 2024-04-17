package com.pepivsky.debtorsapp.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.pepivsky.debtorsapp.data.models.MovementType
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.util.numberValidator
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/*@Composable
fun DisplayDialogAddDebtor(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onAcceptClicked: (name: String, ) -> Unit
) {

}*/
@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun DialogAddDebtor(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    debtor: Debtor? = null,
    onAcceptClicked: (debtor:Debtor) -> Unit,


    ) {
    if (openDialog) {

        Dialog(onDismissRequest = { closeDialog() }) {
            Card(modifier = Modifier
                .fillMaxWidth()
                /*.height(200.dp)
                .padding(16.dp)*/,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors()

            ) {
                Column(
                    modifier = Modifier
                        /*.fillMaxWidth()
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(MaterialTheme.colorScheme.background)*/
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var showDialog by remember { mutableStateOf(false) }
                    val state = rememberDatePickerState()
                    var dateText by remember { mutableStateOf("${LocalDate.now().dayOfMonth}/${LocalDate.now().monthValue}/${LocalDate.now().year}") }

                    var name by rememberSaveable { mutableStateOf(debtor?.name ?: "") }
                    var amount by rememberSaveable { mutableStateOf(  "${debtor?.amount ?: ""}") }
                    var description by rememberSaveable { mutableStateOf( debtor?.description ?: "") }
                    val isEnable by remember { derivedStateOf { name.isNotBlank() && amount.isNotBlank() && description.isNotBlank() } }


                   /* debtor?.let {
                        name = debtor.name
                        amount = debtor.amount.toString()
                        description= debtor.description
                        dateText = debtor.creationDate
                    }*/


                    Text(text = "Nuevo deudor", fontWeight = FontWeight.Bold, fontSize = 18.sp,)
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = { Text(text = "Nombre",) },
                        /*colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFA1824A)

                        ),*/
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words
                        ), /*supportingText = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Nombre vacio",
                            color = MaterialTheme.colorScheme.error)
                    }*/
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = amount,
                        onValueChange = { str ->
                            /*val countDots = str.count {it == '.' } < 2
                            Log.d("pruebilla", "countDots: $countDots")*/
                            if (numberValidator(str)) {
                                amount = str
                            }
                        },
                        label = { Text(text = "Monto",) },
                        /*colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFA1824A)

                        ),*/
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        label = { Text(text = "Concepto",) },
                        /*colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFA1824A)

                        ),*/
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                    )


                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { showDialog = true }) {
                        Text(text = dateText,)
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (debtor == null) {
                                if (name.isNotEmpty() && amount.isNotEmpty() && description.isNotEmpty() && dateText.isNotEmpty()) {
                                    val newDebtor = Debtor(
                                        name = name,
                                        description = description,
                                        creationDate = dateText,
                                        amount = amount.toDoubleOrNull() ?: 0.0,
                                        remaining = amount.toDoubleOrNull() ?: 0.0
                                    )
                                    onAcceptClicked(newDebtor)
                                    closeDialog()
                                }
                            } else {
                                if (name.isNotEmpty() && amount.isNotEmpty() && description.isNotEmpty() && dateText.isNotEmpty()) {
                                    val editedDebtor = debtor.copy(
                                        name = name,
                                        description = description,
                                        creationDate = dateText,
                                        amount = amount.toDoubleOrNull() ?: 0.0,
                                        remaining = amount.toDoubleOrNull() ?: 0.0
                                    )
                                    onAcceptClicked(editedDebtor)
                                    closeDialog()
                                }

                            }

                        },
                        //colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009963)),
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


}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TextFieldCustom() {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        onValueChange = {

        },
        label = { Text(text = "Nombre",) },
        /*colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFA1824A)

        )*/
    )
}
