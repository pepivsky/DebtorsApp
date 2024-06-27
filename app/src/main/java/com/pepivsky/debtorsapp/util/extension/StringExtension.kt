package com.pepivsky.debtorsapp.util.extension

import java.text.DecimalFormat
import java.text.NumberFormat

fun Double.toCurrencyFormat(): String {
    val formatter: NumberFormat = DecimalFormat("$###,###,##0.00")
    return formatter.format(this)
}