package com.pepivsky.debtorsapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
    onAcceptClicked: (name: String, amount: String, description: String, dateText: String) -> Unit,

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

                var name by remember { mutableStateOf("") }
                var amount by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }



                Text(text = "Nuevo deudor", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = { Text(text = "Nombre", color = Color(0xFFA1824A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA1824A)

                    )
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = amount,
                    onValueChange = {
                        amount = it
                    },
                    label = { Text(text = "Monto", color = Color(0xFFA1824A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA1824A)

                    )
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = { Text(text = "Descripcion", color = Color(0xFFA1824A)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFA1824A)

                    )
                )
                // Color(0xFF009963)

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showDialog = true }) {
                    Text(text = dateText, color = Color(0xFF009963))
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onAcceptClicked(name, amount, description, dateText)
                        closeDialog()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009963))
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TextFieldCustom() {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        onValueChange = {

        },
        label = { Text(text = "Nombre", color = Color(0xFFA1824A)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFA1824A)

        )
    )
}
