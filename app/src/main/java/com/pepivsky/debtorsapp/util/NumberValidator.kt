package com.pepivsky.debtorsapp.util

import android.util.Log

fun numberValidator(input: String): Boolean {
    val countDots = input.count {it == '.' } < 2
    // acept 0..9 and '.'
    return input.all { it in '\u0030'..'\u0039' || it == '.' } && countDots
}