package com.pepivsky.debtorsapp.ui.screens.home

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.pepivsky.debtorsapp.components.DialogAddDebtor
import com.pepivsky.debtorsapp.components.ads.showInterstitial
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.ui.viewmodels.SharedViewModel
import com.pepivsky.debtorsapp.navigation.AppScreens
import com.pepivsky.debtorsapp.util.extension.toRidePrice
import com.pepivsky.todocompose.ui.screens.ads.AdvertView


//@Preview
@Composable
fun HomeScreen(viewModel: SharedViewModel, navController: NavController) {
    val allDebtors by viewModel.allDebtors.collectAsState()
    val total by viewModel.totalAmount.collectAsState(0.0)
    var openDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(floatingActionButton = { FabAdd(onFabClicked = { openDialog = true }) }) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val startGuide = createGuidelineFromStart(0.05F)
            val endGuide = createGuidelineFromEnd(0.05F)
            val bottomGuide = createGuidelineFromBottom(0.1F)
            val (titleRef, listRef, totalAmountRef, adRef) = createRefs()

            AdvertView(modifier = Modifier.constrainAs(adRef) {
                top.linkTo(parent.top,)
            })

            HomeTitle(modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(adRef.bottom,)
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
            closeDialog = { openDialog = false }) { debtor ->
                viewModel.addNewDebtor(debtor)
            }
        }
    }


//@Preview(showBackground = true)
@Composable
fun DebtorsList(
    debtors: List<Debtor>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val randomNum = (1..10).random()

    LazyColumn(modifier = modifier) {
        items(debtors, key = { it.debtorId }) { debtor ->
            ItemDebtor(debtor = debtor) {
                if (randomNum == 2 || randomNum == 7 || randomNum == 5) {
                    showInterstitial(context) {
                        navController.navigate(
                            route = AppScreens.MovementsScreen.createRoute(debtor.debtorId)
                        )
                    }
                } else {
                    navController.navigate(
                        route = AppScreens.MovementsScreen.createRoute(debtor.debtorId)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun ItemDebtor(modifier: Modifier = Modifier, debtor: Debtor, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp), onClick = onClick,
        colors = CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconDebtor(
                firstLetter = debtor.name.first(),
                modifier = Modifier.size(40.dp),
                fontSize = 24
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = debtor.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = debtor.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = debtor.creationDate,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "$${debtor.remaining.toRidePrice()}"
            )

        }
    }
}


//@Preview
@Composable
fun FabAdd(modifier: Modifier = Modifier, onFabClicked: () -> Unit) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        onClick = { onFabClicked() }) {
        Icon(
            //modifier = modifier.size(36.dp),
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
    }
}

@Preview
@Composable
fun HomeTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Deudores",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun TotalAmount(modifier: Modifier = Modifier, total: String = "Total: $770.00") {
    Text(modifier = modifier, text = "Total: $$total", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
}

@Preview
@Composable
fun IconDebtor(firstLetter: Char = 'B',modifier: Modifier = Modifier, fontSize: Int = 30) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "$firstLetter",
            fontSize = fontSize.sp, fontWeight = FontWeight.ExtraBold
        )
    }
}