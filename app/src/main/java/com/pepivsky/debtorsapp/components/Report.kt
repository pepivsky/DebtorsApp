package com.pepivsky.debtorsapp.components

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import com.pepivsky.debtorsapp.data.models.MovementType
import com.pepivsky.debtorsapp.data.models.entity.Movement
import com.pepivsky.debtorsapp.util.extension.formatToServerDateDefaults
import com.pepivsky.debtorsapp.util.extension.toCurrencyFormat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class Report @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    suspend fun createPdf(movements: List<Movement>, remaining: Double, debtorName: String): Uri? {
        var fileUri: Uri?

        withContext(Dispatchers.IO) {
            val pdfDocument = PdfDocument()
            val pageWidth = 595
            val pageHeight = 842
            val marginLeft = 20f
            val marginTop = 40f
            val lineSpacing = 20f
            val itemsPerPage = (pageHeight - marginTop).toInt() / lineSpacing.toInt() - 4 // 4 lines reserved for headers and remaining text

            fun drawPage(
                canvas: Canvas,
                movements: List<Movement>,
                startIndex: Int,
                endIndex: Int,
                isLastPage: Boolean
            ) {
                // Paint for regular text
                val regularPaint = Paint().apply {
                    color = Color.BLACK
                    textSize = 12f
                }

                // Paint for bold text
                val boldPaint = Paint(regularPaint).apply {
                    isFakeBoldText = true
                }

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
                for (i in startIndex until endIndex) {
                    xPos = marginLeft
                    val movement = movements[i]
                    canvas.drawText(
                        movement.date.formatToServerDateDefaults(),
                        xPos,
                        yPos,
                        regularPaint
                    )
                    xPos += 150
                    canvas.drawText(movement.concept, xPos, yPos, regularPaint)
                    xPos += 150
                    canvas.drawText(
                        if (movement.type == MovementType.INCREASE) "Aumento" else "Pago",
                        xPos,
                        yPos,
                        regularPaint
                    )
                    xPos += 150
                    canvas.drawText(movement.amount.toCurrencyFormat(), xPos, yPos, regularPaint)
                    yPos += lineSpacing


                }

                if (isLastPage) {
                    yPos += lineSpacing // Leave a gap before the "Restante por pagar" text
                    canvas.drawText(
                        "Restante por pagar: ${remaining.toCurrencyFormat()}",
                        marginLeft,
                        yPos,
                        boldPaint
                    )
                }


            }

            var currentPage = 1
            var currentIndex = 0

            // Draw movements in pages
            while (currentIndex < movements.size) {
                val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPage).create()
                val page = pdfDocument.startPage(pageInfo)
                val canvas: Canvas = page.canvas

                val endIndex = minOf(currentIndex + itemsPerPage, movements.size)
                val isLastPage = (endIndex == movements.size)

                drawPage(canvas, movements, currentIndex, endIndex, isLastPage)

                currentIndex = endIndex
                currentPage++



                pdfDocument.finishPage(page)
            }


            // Guardar el PDF en el almacenamiento interno de la app
            val file = File(context.filesDir, "detalle_deuda_${debtorName}_${System.currentTimeMillis()}.pdf")
            try {
                FileOutputStream(file).use { outputStream ->
                    pdfDocument.writeTo(outputStream)
                }

                // Mostrar toast de éxito
                /*withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "PDF generado exitosamente :)",
                        Toast.LENGTH_LONG
                    ).show()
                }*/

                // Devolver la Uri del archivo guardado
                fileUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )
            } catch (e: IOException) {
                e.printStackTrace()
                // Mostrar toast de error
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error al compartir el archivo :(", Toast.LENGTH_LONG)
                        .show()
                }
                fileUri = null
            } finally {
                pdfDocument.close()
            }
        }
        return fileUri
    }

    fun sharePdf(pdfUri: Uri) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, pdfUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooser = Intent.createChooser(intent, "Compartir PDF")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}