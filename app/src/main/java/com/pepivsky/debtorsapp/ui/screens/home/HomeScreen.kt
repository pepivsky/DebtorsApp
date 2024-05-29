package com.pepivsky.debtorsapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.pepivsky.debtorsapp.R
import com.pepivsky.debtorsapp.components.DialogAddDebtor
import com.pepivsky.debtorsapp.components.ads.adIsLoaded
import com.pepivsky.debtorsapp.components.ads.showInterstitial
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.navigation.AppScreens
import com.pepivsky.debtorsapp.ui.viewmodels.SharedViewModel
import com.pepivsky.debtorsapp.util.extension.formatToServerDateDefaults
import com.pepivsky.debtorsapp.util.extension.toRidePrice
import com.pepivsky.debtorsapp.components.ads.AdvertView


//@Preview
@Composable
fun HomeScreen(viewModel: SharedViewModel, navController: NavController) {
    val allDebtors by viewModel.allDebtors.collectAsState()
    val total by viewModel.totalAmount.collectAsState(0.0)
    var openDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(topBar = {
        HomeAppBar(navController = navController)
    }, floatingActionButton = { FabAdd(onFabClicked = { openDialog = true }) }) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val startGuide = createGuidelineFromStart(0.05F)
            val endGuide = createGuidelineFromEnd(0.05F) // usando porcentajes
            val bottomGuide = createGuidelineFromBottom(0.1F)
            val (listRef, totalAmountRef, adRef) = createRefs()

            AdvertView(modifier = Modifier.constrainAs(adRef) {
                top.linkTo(parent.top)
                height = Dimension.value(50.dp)
                width = Dimension.wrapContent
            })


            ShowContent(
                debtors = allDebtors,
                navController = navController,
                modifier = Modifier.constrainAs(listRef) {
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    top.linkTo(adRef.bottom, margin = 16.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            )

            TotalAmount(
                total = total.toRidePrice(),
                modifier = Modifier.constrainAs(totalAmountRef) {
                    bottom.linkTo(bottomGuide)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                }
            )
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
fun ShowContent(
    debtors: List<Debtor>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    if (debtors.isEmpty()) {
        EmptyContent(modifier = modifier)
    } else {
        val context = LocalContext.current
        val randomNum = (1..10).random()

        LazyColumn(modifier = modifier) {
            items(debtors, key = { it.debtorId }) { debtor ->
                ItemDebtor(debtor = debtor) {
                    if ((randomNum == 2 || randomNum == 7 || randomNum == 5) && adIsLoaded) {
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


}


@Preview
@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(R.drawable.icon_empty), "icon empty content")
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = stringResource(R.string.title_empty_content), textAlign = TextAlign.Center)
        Text(text = stringResource(R.string.label_empty_content), textAlign = TextAlign.Center)
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
                    text = debtor.creationDate.formatToServerDateDefaults(),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "$${debtor.remaining.toRidePrice()}",
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )

        }
    }
}


//@Preview
@Composable
fun FabAdd(modifier: Modifier = Modifier, onFabClicked: () -> Unit) {
    FloatingActionButton(modifier = modifier,
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
        text = stringResource(id = R.string.debtors),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}

//@Preview(showBackground = true)
@Composable
fun TotalAmount(modifier: Modifier = Modifier, total: String) {
    Text(
        modifier = modifier,
        text = "Total: $$total",
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

//@Preview
@Composable
fun IconDebtor(modifier: Modifier = Modifier, firstLetter: Char, fontSize: Int = 30) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "${firstLetter.uppercaseChar()}",
            fontSize = fontSize.sp, fontWeight = FontWeight.ExtraBold
        )
    }
}