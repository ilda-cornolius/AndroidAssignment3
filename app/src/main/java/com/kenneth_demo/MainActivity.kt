package com.kenneth_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import com.kenneth_demo.ui.navigation.AppNavHost
import com.kenneth_demo.ui.theme.WeatherAppTheme

//Using the ExperimentalMaterial3Api class to use with API calls
@OptIn(ExperimentalMaterial3Api::class)
//Start of the program
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //creates and starts the application
        super.onCreate(savedInstanceState)
        //Sets the content of the weather app theme
        setContent {
            WeatherAppTheme {
                Scaffold(
                    topBar = {
                        //Setting up the top bar colors and title text
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Assignment Three Demo")
                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
