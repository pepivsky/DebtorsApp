package com.pepivsky.debtorsapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Preview
@Composable
fun DetailDebtorScreen() {
    Scaffold { paddingValues ->
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

            DebtorInfo(modifier = Modifier.constrainAs(debtorInfoRef) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
            })

            DebtInfo(modifier = Modifier.constrainAs(debtInfoRef) {
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

            MovementsList(modifier = Modifier.constrainAs(movementsListRef) {
                top.linkTo(movementsTitleRef.bottom, margin = 8.dp)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                bottom.linkTo(paymentButtonRef.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            })

            PaymentButton(modifier = Modifier.constrainAs(paymentButtonRef) {
                start.linkTo(startGuide)
                end.linkTo(spacerRef.start)
                bottom.linkTo(bottomGuide)
                width = Dimension.fillToConstraints
            })

            Spacer(modifier = Modifier.size(16.dp).constrainAs(spacerRef) {
                start.linkTo(paymentButtonRef.end)
                end.linkTo(increaseButtonRef.start)
                bottom.linkTo(bottomGuide)
            })
            IncreaseButton(modifier = Modifier.constrainAs(increaseButtonRef) {
                start.linkTo(spacerRef.end)
                end.linkTo(endGuide)
                bottom.linkTo(bottomGuide)
                width = Dimension.fillToConstraints
            })

            //createHorizontalChain(paymentButtonRef, increaseButtonRef, chainStyle = ChainStyle.Packed)


        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovementsList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(20) {
            ItemMovement()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemMovement(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier) {
            Text(
                text = "Pago",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C170D),
                fontSize = 18.sp
            )
            //Text(text = "Celulares", color = Color(0xFFA1824A))
            Text(text = "22 de diciembre del 2023", color = Color(0xFFA1824A))
        }
        Spacer(modifier = Modifier.weight(1F))
        Text(text = "$770.00", color = Color(0xFF1C170D), fontSize = 18.sp)

    }
}

@Preview
@Composable
fun DebtInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            Text(text = "Restante", color = Color(0xFFA1824A))
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "1000.00", color = Color.Black)

        }

        Spacer(modifier = Modifier.size(8.dp))
        Row {
            Text(text = "Deuda", color = Color(0xFFA1824A))
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "3000.00", color = Color.Black)

        }
    }


}

@Preview
@Composable
fun DebtorInfo(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        IconDebtor(modifier = Modifier.size(120.dp), 60)
        Text(text = "Blanquis", color = Color.Black, fontSize = 20.sp)
    }
}

@Preview
@Composable
fun PaymentButton(modifier: Modifier = Modifier) {
    Button(
        modifier = modifier,
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009963))
    ) {
        Text(text = "Pago", color = Color.White)
        //ButtonColors(containerColor = Color(0xFF009963))
    }
}

@Preview
@Composable
fun IncreaseButton(modifier: Modifier = Modifier) {
    Button(
        modifier = modifier,
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F0E5))
    ) {
        Text(text = "Aumento", color = Color.Black)
        //ButtonColors(containerColor = Color(0xFF009963))
    }
}