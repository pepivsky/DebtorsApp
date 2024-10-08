package com.pepivsky.debtorsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pepivsky.debtorsapp.components.ads.loadInterstitial
import com.pepivsky.debtorsapp.components.ads.removeInterstitial
import com.pepivsky.debtorsapp.ui.viewmodels.SharedViewModel
import com.pepivsky.debtorsapp.navigation.AppNavigation
import com.pepivsky.debtorsapp.ui.theme.DebtorsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //private lateinit var debtorsDatabase: DebtorsDatabase
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashScreen()
        loadInterstitial(this)


        //debtorsDatabase = (application as DebtorsApplication).database
        /*debtorsDatabase.getDebtorDAO().getDebtorWithMovements().forEach {
            println(it.debtor.name)
            it.movements.forEach { println(it.type) }
        }*/

        //Toast.makeText(this, debtorsDatabase.getDebtorDAO().getDebtorWithMovements().joinToString(), Toast.LENGTH_LONG).show()

        //val list = debtorsDatabase.getDebtorDAO().getDebtorWithMovements().joinToString()
        //val list = debtorsDatabase.getDebtorDAO().getMovementsByDebtor(1).joinToString()


        setContent {
            DebtorsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    tonalElevation = 5.dp,
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    // for manage dialog
                   /* var openDialog by rememberSaveable { mutableStateOf(false) }

                    HomeScreen(viewModel = sharedViewModel, onFabClicked = {
                        openDialog = true
                    })*/

                    //HomeScreen(viewModel = sharedViewModel)
                    AppNavigation(viewModel = sharedViewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        removeInterstitial()
        super.onDestroy()
    }

    private fun setupSplashScreen() {
        var keepSplashScreenOn = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.showSplash.collect {
                    keepSplashScreenOn = it
                }
            }
        }

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreenOn
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DebtorsAppTheme {
        Greeting("Android")
    }
}