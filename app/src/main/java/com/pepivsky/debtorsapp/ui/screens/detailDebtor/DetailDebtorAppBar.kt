package com.pepivsky.debtorsapp.ui.screens.detailDebtor

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pepivsky.debtorsapp.R
import com.pepivsky.debtorsapp.components.DialogAddDebtor
import com.pepivsky.debtorsapp.data.models.entity.DebtorWithMovements
import com.pepivsky.debtorsapp.ui.viewmodels.SharedViewModel


//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultDetailDebtorAppBar(
    sharedViewModel: SharedViewModel,
    navController: NavController,
    debtorWithMovements: DebtorWithMovements,
    onEditClicked: () -> Unit,

    ) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(),
        title = {
            Text(
                text = debtorWithMovements.debtor.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp

            )
        },
        actions = {


            DetailDebtorAppBarActions(onDeleteClicked = {
                sharedViewModel.deleteSelectedDebtor(debtorWithMovements.debtor)
                navController.popBackStack()
            }, onEditClicked = {
                onEditClicked()
            })


        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

        })
}

@Composable
fun DetailDebtorAppBarActions(
    onDeleteClicked: () -> Unit,
    onEditClicked: () -> Unit,

    ) {
    var showDialogConfirmDelete by remember { mutableStateOf(false) }

    if (showDialogConfirmDelete) {
        AlertDialog(
            onDismissRequest = { showDialogConfirmDelete = false },
            title = { Text(stringResource(R.string.title_dialog_delete_debtor)) },
            text = { Text(stringResource(R.string.text_dialog_delete_debtor)) },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteClicked()
                }) {
                    Text(stringResource(R.string.delete).uppercase())
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialogConfirmDelete = false }) {
                    Text(stringResource(R.string.cancel).uppercase())
                }
            },
        )
    }
    DropDownActions(
        onDelete = { showDialogConfirmDelete = true },
        onEdit = { onEditClicked() }
    )
}

// delete all action
@Composable
fun DropDownActions(onDelete: () -> Unit, onEdit: () -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "",
        )
    }
    // dropdown menu with prioritys
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = {
            Text(text = stringResource(R.string.label_delete))
        }, onClick = {
            expanded = false
            onDelete()
        })

        DropdownMenuItem(text = {
            Text(text = stringResource(R.string.label_edit))
        }, onClick = {
            expanded = false
            onEdit()
        })
    }
}