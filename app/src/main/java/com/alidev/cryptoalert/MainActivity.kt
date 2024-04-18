package com.alidev.cryptoalert

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alidev.cryptoalert.service.CryptoAlertService
import com.alidev.cryptoalert.ui.screen.MainScreen
import com.alidev.cryptoalert.ui.theme.CryptoAlertTheme
import com.alidev.cryptoalert.ui.viewmodel.stats.CryptoMarketState
import com.alidev.cryptoalert.ui.viewmodel.stats.CryptoMarketViewModel
import com.alidev.cryptoalert.ui.viewmodel.stats.EmptyMarketState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<CryptoMarketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAlertTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF1E1E1E)
                ) {

                    val state = viewModel.state.collectAsState().value
                    when (state) {
                        is EmptyMarketState -> {

                        }

                        is CryptoMarketState -> {
                            MainScreen(
                                cryptos = state.cryptos,
                                cryptoConditions = state.cryptoConditions,
                                dstCurrency = state.dstCurrency,
                                onAddConditionClick = {
                                    if (!CryptoAlertService.isServiceStarted) {
                                        viewModel.addCondition(it)
                                    }else {
                                        Toast.makeText(this, "When Service is running, you cannot modify conditions!", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onRemoveConditionClick = {
                                    if (!CryptoAlertService.isServiceStarted) {
                                        viewModel.removeCondition(it)
                                    }else {
                                        Toast.makeText(this, "When Service is running, you cannot modify conditions!", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onStartServiceClick = {
                                    if (!CryptoAlertService.isServiceStarted) {
                                        CryptoAlertService.start(this)
                                    }else {
                                        Toast.makeText(this, "Service is running!", Toast.LENGTH_SHORT).show()
                                    }

                                },
                                onStopServiceClick = {
                                    if (CryptoAlertService.isServiceStarted) {
                                        CryptoAlertService.stop(this)
                                    }else {
                                        Toast.makeText(this, "Service is not running!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}