package com.pepivsky.debtorsapp

import android.app.Application
import androidx.room.Room
import com.pepivsky.debtorsapp.util.Constants
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DebtorsApplication: Application() {
    /*val database: DebtorsDatabase by lazy {
        Room.databaseBuilder(
            context = this,
            klass = DebtorsDatabase::class.java,
            name = "pp"
        ).allowMainThreadQueries().build()
    }*/
}