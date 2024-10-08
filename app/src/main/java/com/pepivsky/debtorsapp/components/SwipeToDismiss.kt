package com.pepivsky.debtorsapp.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun DemoSwipeToDismiss(
    modifier: Modifier = Modifier
) {
    var myList by remember {
        mutableStateOf((1..3).toList())
    }

    LazyColumn(modifier = modifier) {
        items(myList) { item ->
            SwipeBox(
                onDelete = {
                    // Just for Example. Is not optimal!
                    myList = myList.toMutableList().also { it.remove(item) }
                },
                onEdit = { },
                modifier = Modifier.animateItemPlacement()
            ) {
                ListItem(headlineContent = { Text(text = "Headline text $item") },
                    supportingContent = { Text(text = "Supporting text $item") },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.AccountBox,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    // grados para animar
    val degreesDeleteIcon by animateFloatAsState(
        targetValue = if (swipeState.currentValue == swipeState.targetValue) {
            0F
        } else {
            -45F
        }
    )

    val degreesEditIcon by animateFloatAsState(
        targetValue = if (swipeState.currentValue == swipeState.targetValue) {
            -45F
        } else {
            0F
        }
    )



    /*swipeState.currentValue
    swipeState.targetValue

    swipeState.progress*/

    lateinit var icon: ImageVector
    lateinit var alignment: Alignment
    val backgroundColor: Color
    val degrees: Float
    val iconColor: Color

    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            backgroundColor = MaterialTheme.colorScheme.errorContainer
            iconColor = MaterialTheme.colorScheme.onErrorContainer
            degrees = degreesDeleteIcon
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            icon = Icons.Outlined.Edit
            alignment = Alignment.CenterStart
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
            iconColor = MaterialTheme.colorScheme.onPrimaryContainer
            degrees = degreesEditIcon
        }

        SwipeToDismissBoxValue.Settled -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            backgroundColor = CardDefaults.cardColors().containerColor
            degrees = 0F
            iconColor = Color.Unspecified
        }
    }

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    //.padding(vertical = 4.dp)
                    .clip(shape = CardDefaults.shape)
                    .fillMaxSize()
                    .background(backgroundColor)

            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize().rotate(degrees),
                    imageVector = icon, contentDescription = null,
                    tint = iconColor
                )
            }
        }
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> {
            onDelete()
        }

        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(swipeState) {
                onEdit()
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }

        SwipeToDismissBoxValue.Settled -> {
        }
    }
}