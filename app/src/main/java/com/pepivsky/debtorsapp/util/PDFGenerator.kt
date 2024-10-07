package com.pepivsky.debtorsapp.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.pepivsky.debtorsapp.R
import java.io.File
import java.io.FileOutputStream

// on below line we are creating a generate PDF
// method which is use to generate our PDF file.
/*
fun generatePDF(context: Context) {

    // declaring width and height
    // for our PDF file.
    var pageHeight = 1120
    var pageWidth = 792

    // creating a bitmap variable
    // for storing our images
    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    // creating an object variable
    // for our PDF document.
    var pdfDocument: PdfDocument = PdfDocument()

    // two variables for paint "paint" is used
    // for drawing shapes and we will use "title"
    // for adding text in our PDF file.
    var paint: Paint = Paint()
    var title: Paint = Paint()

    // on below line we are initializing our bitmap and scaled bitmap.
    bmp = BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_foreground)
    scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false)


    // we are adding page info to our PDF file
    // in which we will be passing our pageWidth,
    // pageHeight and number of pages and after that
    // we are calling it to create our PDF.
    var myPageInfo: PdfDocument.PageInfo? =
        PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

    // below line is used for setting
    // start page for our PDF file.
    var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

    // creating a variable for canvas
    // from our page of PDF.
    var canvas: Canvas = myPage.canvas

    // below line is used to draw our image on our PDF file.
    // the first parameter of our drawbitmap method is
    // our bitmap
    // second parameter is position from left
    // third parameter is position from top and last
    // one is our variable for paint.
    canvas.drawBitmap(scaledbmp, 56F, 40F, paint)

    // below line is used for adding typeface for
    // our text which we will be adding in our PDF file.
    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

    // below line is used for setting text size
    // which we will be displaying in our PDF file.
    title.textSize = 15F

    // below line is sued for setting color
    // of our text inside our PDF file.
    title.setColor(ContextCompat.getColor(context, R.color.purple_200))

    // below line is used to draw text in our PDF file.
    // the first parameter is our text, second parameter
    // is position from start, third parameter is position from top
    // and then we are passing our variable of paint which is title.
    canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
    canvas.drawText("Geeks for Geeks", 209F, 80F, title)
    title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
    title.setColor(ContextCompat.getColor(context, R.color.purple_200))
    title.textSize = 15F

    // below line is used for setting
    // our text to center of PDF.
    title.textAlign = Paint.Align.CENTER
    canvas.drawText("This is sample document which we have created.", 396F, 560F, title)

    // after adding all attributes to our
    // PDF file we will be finishing our page.
    pdfDocument.finishPage(myPage)

    // below line is used to set the name of
    // our PDF file and its path.
    val file: File = File(Environment.getExternalStorageDirectory(), "GFG.pdf")

    try {
        // after creating a file name we will
        // write our PDF file to that location.
        pdfDocument.writeTo(FileOutputStream(file))

        // on below line we are displaying a toast message as PDF file generated..
        Toast.makeText(context, "PDF file generated..", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        // below line is used
        // to handle error
        e.printStackTrace()

        // on below line we are displaying a toast message as fail to generate PDF
        Toast.makeText(context, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
            .show()
    }
    // after storing our pdf to that
    // location we are closing our PDF file.
    pdfDocument.close()
}

fun checkPermissions(context: Context): Boolean {
    // on below line we are creating a variable for both of our permissions.

    // on below line we are creating a variable for writing to external storage permission
    var writeStoragePermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    // on below line we are creating a variable for
    // reading external storage permission
    var readStoragePermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    // on below line we are returning true if both the
    // permissions are granted and returning false if permissions are not granted.
    return writeStoragePermission == PackageManager.PERMISSION_GRANTED && readStoragePermission == PackageManager.PERMISSION_GRANTED
}

// on below line we are creating a function to request permission.
fun requestPermission(activity: Activity) {

    // on below line we are requesting read and write to
    // storage permission for our application.
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ), 101
    )
}*/

// Request code for creating a PDF document.
const val CREATE_FILE = 1
// Declare the ActivityResultLauncher
private lateinit var createFileLauncher: ActivityResultLauncher<Intent>

/*
private fun createFile(pickerInitialUri: Uri) {
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"
        putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker before your app creates the document.
        putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
    }
    //startActivityForResult(intent, CREATE_FILE)
    createFileLauncher.launch(intent)
}*/
// Request code for creating a PDF document.

/*
private fun createFile(pickerInitialUri: Uri) {
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"
        putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker before your app creates the document.
        putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
    }
    startActivityForResult(intent, CREATE_FILE)
}*/
