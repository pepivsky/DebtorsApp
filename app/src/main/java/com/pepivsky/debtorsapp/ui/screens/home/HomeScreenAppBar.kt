package com.pepivsky.debtorsapp.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pepivsky.debtorsapp.R
import com.pepivsky.debtorsapp.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(navController: NavController) {
    CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.debtors), fontSize = 24.sp) },
        actions = {
            IconButton(onClick = {
                navController.navigate(
                    route = AppScreens.AboutScreen.route
                )
            }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "back",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    )
}
