package com.pepivsky.debtorsapp.util.extension

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate
import java.util.Locale


/**
 * Pattern: yyyy-MM-dd
 */
fun LocalDate.formatToServerDateDefaults(): String{
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return this.format(formatter)
}