package com.pepivsky.debtorsapp.util

import java.text.DecimalFormat
import java.text.NumberFormat

fun Double.toRidePrice(): String {
    val formatter: NumberFormat = DecimalFormat("#,###")
    return formatter.format(this)
}