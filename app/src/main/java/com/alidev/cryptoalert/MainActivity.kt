package com.alidev.cryptoalert

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alidev.cryptoalert.service.CryptoAlertService
import com.alidev.cryptoalert.ui.theme.CryptoAlertTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAlertTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(
                            onClick = {
                                Intent(applicationContext, CryptoAlertService::class.java).also {
                                    it.action = CryptoAlertService.START
                                    startForegroundService(it)
                                }
                            }
                        ) {
                            Text(text = "Start Service")
                        }

                        Button(
                            onClick = {
                                Intent(applicationContext, CryptoAlertService::class.java).also {
                                    it.action = CryptoAlertService.STOP
                                    startForegroundService(it)
                                }
                            }
                        ) {
                            Text(text = "Stop Service")
                        }
                    }
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
    CryptoAlertTheme {
        Greeting("Android")
    }
}