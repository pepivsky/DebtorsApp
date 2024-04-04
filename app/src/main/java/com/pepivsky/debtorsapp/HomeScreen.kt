package com.pepivsky.debtorsapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.pepivsky.debtorsapp.components.DialogAddDebtor
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.data.models.SharedViewModel
import com.pepivsky.debtorsapp.navigation.AppScreens
import com.pepivsky.debtorsapp.util.toRidePrice
import com.pepivsky.todocompose.ui.screens.ads.AdvertView


//@Preview
@Composable
fun HomeScreen(viewModel: SharedViewModel, navController: NavController) {
    val allDebtors by viewModel.allDebtors.collectAsState()
    val total by viewModel.totalAmount.collectAsState(0.0)
    var openDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(contentColor = Color.White, floatingActionButton = { FabAdd(onFabClicked = { openDialog = true }) }) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val startGuide = createGuidelineFromStart(0.05F)
            val endGuide = createGuidelineFromEnd(0.05F)
            val bottomGuide = createGuidelineFromBottom(0.1F)
            val (titleRef, listRef, totalAmountRef, adRef) = createRefs()

            HomeTitle(modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(startGuide)
                /*end.linkTo(endGuide)
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints*/
            })

            DebtorsList(debtors = allDebtors, navController = navController,modifier = Modifier.constrainAs(listRef) {
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                top.linkTo(titleRef.bottom, margin = 32.dp)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            })

            TotalAmount(total = total.toRidePrice(), modifier = Modifier.constrainAs(totalAmountRef) {
                bottom.linkTo(bottomGuide)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                height = Dimension.wrapContent
                width = Dimension.wrapContent
            })

           /* AdvertView(modifier = Modifier.constrainAs(adRef) {
                top.linkTo(bottomGuide)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                height = Dimension.wrapContent
                width = Dimension.wrapContent
            })*/


        }

        DialogAddDebtor(
            openDialog = openDialog,
            closeDialog = { openDialog = false }) { name, amount, description, date ->
            if (name.isNotEmpty() && amount.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()) {
                val debtor = Debtor(
                    name = name,
                    description = description,
                    creationDate = date,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    remaining = amount.toDoubleOrNull() ?: 0.0
                )

                viewModel.addNewDebtor(debtor)
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DebtorsList(debtors: List<Debtor>, navController: NavController, modifier: Modifier = Modifier,) {
    LazyColumn(modifier = modifier) {
        item {
            AdvertView()
        }
        items(debtors, key = { it.debtorId }) { debtor ->
            ItemDebtor(debtor = debtor) {
                navController.navigate(
                    route = AppScreens.MovementsScreen.createRoute(debtor.debtorId)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun ItemDebtor(modifier: Modifier = Modifier, debtor: Debtor, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDebtor(firstLetter = debtor.name.first(),modifier = Modifier.size(40.dp), fontSize = 24)
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = debtor.name,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C170D),
                fontSize = 18.sp
            )
            Text(text = debtor.description, color = Color(0xFFA1824A))
            Text(text = debtor.creationDate, color = Color(0xFFA1824A))
        }
        Spacer(modifier = Modifier.weight(1F))
        Text(text = "$${debtor.remaining.toRidePrice()}", color = Color(0xFF1C170D), fontSize = 18.sp)

    }
}


//@Preview
@Composable
fun FabAdd(modifier: Modifier = Modifier, onFabClicked: () -> Unit) {
    FloatingActionButton(onClick = { onFabClicked() }, containerColor = Color(0xFF009963)) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
    }
}

@Preview
@Composable
fun HomeTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Deudores",
        fontSize = 30.sp,
        color = Color(0xFF1C170D),
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun TotalAmount(modifier: Modifier = Modifier, total: String = "Total: $770.00") {
    Text(modifier = modifier, text = "Total: $$total", color = Color(0xFF1C170D), fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
}

@Preview
@Composable
fun IconDebtor(firstLetter: Char = 'B',modifier: Modifier = Modifier, fontSize: Int = 30) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.DarkGray)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "$firstLetter",
            color = Color.White, fontSize = fontSize.sp, fontWeight = FontWeight.ExtraBold
        )
    }
}