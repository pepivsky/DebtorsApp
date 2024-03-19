package com.pepivsky.debtorsapp.di

import android.content.Context
import androidx.room.Room
import com.pepivsky.debtorsapp.DebtorsDatabase
import com.pepivsky.debtorsapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DebtorsDatabase::class.java, Constants.DEBTORS_DATABASE). build()

    @Singleton
    @Provides
    fun provideDebtorDAO(database: DebtorsDatabase) = database.getDebtorDAO()

    @Singleton
    @Provides
    fun provideMovementsDAO(database: DebtorsDatabase) = database.getMovementDAO()
}
