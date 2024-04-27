package com.pepivsky.debtorsapp.ui.screens.detailDebtor

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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


/*

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {

    // for manage dialog
    var openDialog by rememberSaveable { mutableStateOf(false) }

    DisplayAlertDialog(title = stringResource(id = R.string.delete_all_tasks), message = stringResource(
        id = R.string.delete_all_tasks_confirmation
    ), openDialog = openDialog, closeDialog = { openDialog = false }, onYesClicked = {
    onDeleteAllConfirmed()
    })

    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteAllConfirmed = { openDialog = true })
}
 */

/*
// app bar que se muestra por default
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_bar_title), color = MaterialTheme.colors.topAppBarContentColor) },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        }
    )
}
 */

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
            }, onEditClicked =  {
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
    DropDownActions(
        onDelete = { onDeleteClicked() },
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