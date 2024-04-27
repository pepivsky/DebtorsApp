package com.pepivsky.debtorsapp.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.pepivsky.debtorsapp.BuildConfig
import com.pepivsky.debtorsapp.R


//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(),
            title = {
                Text(
                    text = stringResource(R.string.label_configuration),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp

                )
            }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack()  }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            })
    }) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val startGuide = createGuidelineFromStart(0.05F)
            val endGuide = createGuidelineFromEnd(0.05F)
            //val bottomGuide = createGuidelineFromBottom(0.02F)
            val listRef = createRef()
            SetupList(modifier = Modifier.constrainAs(listRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            })

        }
    }
}



fun shareLink(context: Context) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.pepivsky.debtorsapp&pcampaignid=web_share")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(context, shareIntent, null)
}
@Composable
fun SetupList(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    LazyColumn(modifier = modifier) {
        item {
            SetupItem(
                title = stringResource(R.string.label_share),
                content = stringResource(R.string.label_shareapp_content),
                icon = Icons.Default.Share
            ) {

                /*val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(context, shareIntent, null)*/
                shareLink(context)
            }
        }

        item {
            SetupItem(
                title = "Versión de la aplicación",
                content = BuildConfig.VERSION_NAME,
                icon = Icons.Default.Code
            ) {

            }
        }

        item {
            SetupItem(
                title = "Sugerencias",
                content = "Envíanos tus sugerencias y comentarios",
                icon = Icons.Default.Mail
            ) {
            composeEmail(
                arrayOf("pepivskydev@gmail.com"),
                "Sugerencia app",
                context
            )
            }
        }



        /*item {
            SetupItem(
                title = "Calificar",
                content = "Ayuda a mas personas a llevar el control de sus dedudores"
            ) {}
        }*/
    }
}

fun composeEmail(addresses: Array<String?>?, subject: String?, context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.setData(Uri.parse("mailto:")) // only email apps should handle this
    intent.putExtra(Intent.EXTRA_EMAIL, addresses)
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

//@Preview(showBackground = true)
@Composable
fun SetupItem(
    title: String,
    content: String,
    icon: ImageVector,
    action: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { action() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.tertiary
        )

        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}