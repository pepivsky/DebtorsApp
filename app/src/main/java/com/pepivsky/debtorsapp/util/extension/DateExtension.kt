package com.pepivsky.debtorsapp.util.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Pattern: yyyy-MM-dd
 */
fun Date.formatToServerDateDefaults(): String{
    val sdf= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}