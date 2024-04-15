package com.pepivsky.debtorsapp.ui.screens.detailDebtor

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.pepivsky.debtorsapp.components.DialogAddMovement
import com.pepivsky.debtorsapp.data.models.entity.Debtor
import com.pepivsky.debtorsapp.data.models.entity.DebtorWithMovements
import com.pepivsky.debtorsapp.data.models.entity.Movement
import com.pepivsky.debtorsapp.data.models.MovementType
import com.pepivsky.debtorsapp.ui.viewmodels.SharedViewModel
import com.pepivsky.debtorsapp.util.toRidePrice
import com.pepivsky.debtorsapp.ui.screens.home.IconDebtor


//@Preview
@Composable
fun DetailDebtorScreen(
    viewModel: SharedViewModel,
    navController: NavController,
    selectedDebtor: DebtorWithMovements
) {
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var movementType by rememberSaveable {
        mutableStateOf(MovementType.PAYMENT)
    }

    Scaffold(topBar = { DefaultDetailDebtorAppBar(sharedViewModel = viewModel, navController= navController, debtorWithMovements = selectedDebtor) }) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val startGuide = createGuidelineFromStart(0.05F)
            val endGuide = createGuidelineFromEnd(0.05F)
            val bottomGuide = createGuidelineFromBottom(0.02F)

            val (debtorInfoRef, debtInfoRef, movementsTitleRef, movementsListRef,
                increaseButtonRef,
                paymentButtonRef,
                spacerRef
            ) = createRefs()

            DebtorName(
                name = selectedDebtor.debtor.name,
                modifier = Modifier.constrainAs(debtorInfoRef) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                })

            CardDebtInfo(
                amount = selectedDebtor.debtor.amount,
                remaining = selectedDebtor.debtor.remaining,
                modifier = Modifier.constrainAs(debtInfoRef) {
                    top.linkTo(debtorInfoRef.bottom, margin = 24.dp)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                })

            Text(
                text = "Movimientos",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(movementsTitleRef) {
                    top.linkTo(debtInfoRef.bottom, margin = 24.dp)
                    start.linkTo(startGuide)
                })

            MovementsList(
                selectedDebtor.movements,
                modifier = Modifier.constrainAs(movementsListRef) {
                    top.linkTo(movementsTitleRef.bottom, margin = 8.dp)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(paymentButtonRef.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                })

            PaymentButton(onClick = {
                openDialog = true
                movementType = MovementType.PAYMENT
            }, modifier = Modifier.constrainAs(paymentButtonRef) {
                start.linkTo(startGuide)
                end.linkTo(spacerRef.start)
                bottom.linkTo(bottomGuide)
                width = Dimension.fillToConstraints
            })

            Spacer(modifier = Modifier
                .size(16.dp)
                .constrainAs(spacerRef) {
                    start.linkTo(paymentButtonRef.end)
                    end.linkTo(increaseButtonRef.start)
                    bottom.linkTo(bottomGuide)
                })
            IncreaseButton(onClick = {
                openDialog = true
                movementType = MovementType.INCREASE
            }, modifier = Modifier.constrainAs(increaseButtonRef) {
                start.linkTo(spacerRef.end)
                end.linkTo(endGuide)
                bottom.linkTo(bottomGuide)
                width = Dimension.fillToConstraints
            })

            //createHorizontalChain(paymentButtonRef, increaseButtonRef, chainStyle = ChainStyle.Packed)


        }
    }

    DialogAddMovement(
        movementType = movementType,
        openDialog = openDialog,
        closeDialog = { openDialog = false }) { amount, dateText ->

        val debtorUpdated: Debtor = when (movementType) {
            MovementType.PAYMENT -> {
                selectedDebtor.debtor.copy(remaining = selectedDebtor.debtor.remaining - amount.toDouble())
            }

            MovementType.INCREASE -> {
                selectedDebtor.debtor.copy(
                    remaining = selectedDebtor.debtor.remaining + amount.toDouble(),
                    amount = selectedDebtor.debtor.amount + amount.toDouble()
                )

            }
        }
        val movement = Movement(
            debtorCreatorId = selectedDebtor.debtor.debtorId,
            type = movementType.name,
            amount = amount.toDouble(),
            date = dateText
        )
        viewModel.addMovementTransaction(debtor = debtorUpdated, movement = movement)
    }
}

//@Preview(showBackground = true)
@Composable
fun MovementsList(movements: List<Movement>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(movements) { movement ->
            ItemMovement(movement)
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun ItemMovement(
    movement: Movement,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            modifier = modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = if (movement.type == MovementType.INCREASE.name) "Aumento" else "Pago",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = movement.date)
            }
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "$${movement.amount.toRidePrice()}", fontSize = 18.sp)
        }
    }
}

//@Preview
@Composable
fun DebtInfo(
    amount: Double,
    remaining: Double,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            Text(text = "Restante", fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "$${remaining.toRidePrice()}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        }

        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Text(text = "Deuda", fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "$${amount.toRidePrice()}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardDebtInfo(
    amount: Double = 1200.0,
    remaining: Double = 200.0,
    modifier: Modifier = Modifier
) {
//                     color = MaterialTheme.colorScheme.background
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Text(text = "Restante", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1F))
                Text(
                    text = "$${remaining.toRidePrice()}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    //color = MaterialTheme.colorScheme.onTertiaryContainer
                )

            }
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                Text(text = "Deuda", fontSize = 18.sp,)
                Spacer(modifier = Modifier.weight(1F))
                Text(text = "$${amount.toRidePrice()}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            }
        }
    }
}


@Preview
@Composable
fun DebtorName(name: String = "Blanquis", modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        IconDebtor(firstLetter = name.first(), modifier = Modifier.size(120.dp), fontSize = 60)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = name, fontSize = 20.sp)
    }
}

//@Preview
@Composable
fun PaymentButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        //colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009963))
    ) {
        Text(text = "Pago")
    }
}

//@Preview
@Composable
fun IncreaseButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        //colors = ButtonDefaults.buttonColors(containerColor = )
    ) {
        Text(text = "Aumento")
        //ButtonColors(containerColor = Color(0xFF009963))
    }
}