package com.pepivsky.debtorsapp.ui.screens.detailDebtor

import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.pepivsky.debtorsapp.R
import com.pepivsky.debtorsapp.data.models.entity.DebtorWithMovements
import com.pepivsky.debtorsapp.ui.viewmodels.SharedViewModel
//import com.pepivsky.debtorsapp.util.generatePDF
import android.content.ContentResolver
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import com.pepivsky.debtorsapp.data.models.MovementType
import com.pepivsky.debtorsapp.data.models.entity.Movement
import com.pepivsky.debtorsapp.util.extension.formatToServerDateDefaults
import com.pepivsky.debtorsapp.util.extension.toRidePrice
import java.io.FileOutputStream
import java.io.IOException
import java.time.format.DateTimeFormatter


//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultDetailDebtorAppBar(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    debtorWithMovements: DebtorWithMovements,
    onEditClicked: () -> Unit,

    ) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(),
        title = {
            Text(
                text = debtorWithMovements.debtor.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp

            )
        },
        actions = {


            DetailDebtorAppBarActions(onDeleteClicked = {
                sharedViewModel.deleteSelectedDebtor(debtorWithMovements.debtor)
                navController.popBackStack()
            }, onEditClicked = {
                onEditClicked()
            }, debtorWithMovements)


        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

        })
}

@Composable
fun DetailDebtorAppBarActions(
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,
    debtorWithMovements: DebtorWithMovements,
    ) {
    var showDialogConfirmDelete by remember { mutableStateOf(false) }

    if (showDialogConfirmDelete) {
        AlertDialog(
            onDismissRequest = { showDialogConfirmDelete = false },
            title = { Text(stringResource(R.string.title_dialog_delete_debtor)) },
            text = { Text(stringResource(R.string.text_dialog_delete_debtor)) },
            confirmButton = {
                Button(onClick = {
                    onDeleteClicked()
                }) {
                    Text(stringResource(R.string.delete).uppercase())
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialogConfirmDelete = false }) {
                    Text(stringResource(R.string.cancel).uppercase())
                }
            },
        )
    }
    val contentResolver = LocalContext.current.contentResolver
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { selectedUri ->
        if (selectedUri != null) {
            createPdf(context, selectedUri, debtorWithMovements.movements, debtorWithMovements.debtor.remaining)
        } else {
            println("No file was selected")
        }
    }

    val pickerInitialUri: Uri = "content://com.android.externalstorage.documents/document/primary".toUri()
    DropDownActions(
        onDelete = { showDialogConfirmDelete = true },
        onEdit = { onEditClicked() },
        onGeneratePDF = {
            launcher.launch("detalle_deuda_${debtorWithMovements.debtor.name}_${System.currentTimeMillis()}.pdf")
        }
    )
}
fun createPdf(context: Context, uri: Uri, movements: List<Movement>, remaining: Double) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas: Canvas = page.canvas

    // Paint for regular text
    val regularPaint = Paint().apply {
        color = Color.BLACK
        textSize = 12f
    }

    // Paint for bold text
    val boldPaint = Paint(regularPaint).apply {
        isFakeBoldText = true
    }

    val marginLeft = 20f
    val marginTop = 40f
    val lineSpacing = 20f

    // Draw headers with bold text
    var xPos = marginLeft
    var yPos = marginTop
    canvas.drawText("Fecha", xPos, yPos, boldPaint)
    xPos += 150
    canvas.drawText("Concepto", xPos, yPos, boldPaint)
    xPos += 150
    canvas.drawText("Tipo de movimiento", xPos, yPos, boldPaint)
    xPos += 150
    canvas.drawText("Monto", xPos, yPos, boldPaint)

    // Draw each movement with regular text
    yPos += lineSpacing
    for (movement in movements) {
        xPos = marginLeft
        canvas.drawText(movement.date.formatToServerDateDefaults(), xPos, yPos, regularPaint)
        xPos += 150
        canvas.drawText(movement.concept, xPos, yPos, regularPaint)
        xPos += 150
        canvas.drawText(if (movement.type == MovementType.INCREASE) "Aumento" else "Pago", xPos, yPos, regularPaint)
        xPos += 150
        canvas.drawText("$${movement.amount.toRidePrice()}", xPos, yPos, regularPaint)
        yPos += lineSpacing
    }

    // Draw "Restante:" text below the last amount with bold text
    yPos += lineSpacing
    xPos = marginLeft
    canvas.drawText("Restante por pagar: $${remaining.toRidePrice()}", xPos, yPos, boldPaint)

    pdfDocument.finishPage(page)

    try {
        context.contentResolver.openFileDescriptor(uri, "w")?.use {
            FileOutputStream(it.fileDescriptor).use { outputStream ->
                pdfDocument.writeTo(outputStream)

                // Show success toast
                Toast.makeText(context, "PDF generado exitosamente :)", Toast.LENGTH_LONG).show()
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        // Show error toast
        Toast.makeText(context, "Error al guardar el archivo :(", Toast.LENGTH_LONG).show()
    } finally {
        pdfDocument.close()
    }
}

// delete all action
@Composable
fun DropDownActions(onDelete: () -> Unit, onEdit: () -> Unit, onGeneratePDF: () -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "",
        )
    }
    // dropdown menu with prioritys
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = {
            Text(text = stringResource(R.string.label_delete))
        }, onClick = {
            expanded = false
            onDelete()
        })

        DropdownMenuItem(text = {
            Text(text = stringResource(R.string.label_edit))
        }, onClick = {
            expanded = false
            onEdit()
        })

        DropdownMenuItem(text = {
            Text(text = "Generar PDF")
        }, onClick = {
            expanded = false
            onGeneratePDF()
        })


    }
}