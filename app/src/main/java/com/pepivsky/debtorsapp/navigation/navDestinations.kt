package com.pepivsky.debtorsapp.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreenNav

@Serializable
data class DetailDebtorScreenNav(val debtorId: Long)

@Serializable
object SettingsScreenNav