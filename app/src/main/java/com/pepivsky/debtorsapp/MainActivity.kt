package com.pepivsky.debtorsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pepivsky.debtorsapp.components.DialogAddDebtor
import com.pepivsky.debtorsapp.data.models.Debtor
import com.pepivsky.debtorsapp.data.models.SharedViewModel
import com.pepivsky.debtorsapp.ui.theme.DebtorsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //private lateinit var debtorsDatabase: DebtorsDatabase
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    // for manage dialog
                   /* var openDialog by rememberSaveable { mutableStateOf(false) }

                    HomeScreen(viewModel = sharedViewModel, onFabClicked = {
                        openDialog = true
                    })*/

                    HomeScreen(viewModel = sharedViewModel)

                }
            }
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