package com.alidev.cryptoalert

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.alidev.cryptoalert.service.CryptoAlertService
import com.alidev.cryptoalert.ui.screen.MainScreen
import com.alidev.cryptoalert.ui.theme.CryptoAlertTheme
import com.alidev.cryptoalert.ui.viewmodel.CryptoMarketState
import com.alidev.cryptoalert.ui.viewmodel.CryptoMarketViewModel
import com.alidev.cryptoalert.ui.viewmodel.EmptyMarketState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<CryptoMarketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    startService()
                }
            }

            val context = LocalContext.current

            var isDarkMode by rememberSaveable {
                mutableStateOf(false)
            }

            CryptoAlertTheme(
                darkTheme = isDarkMode
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val state = viewModel.state.collectAsState().value
                    when (state) {
                        is EmptyMarketState -> {

                        }

                        is CryptoMarketState -> {
                            MainScreen(
                                cryptos = state.cryptos,
                                cryptoConditions = state.cryptoConditions,
                                indicators = state.indicators,
                                dstCurrency = state.dstCurrency,
                                isDarkMode = isDarkMode,
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
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        when (PackageManager.PERMISSION_GRANTED) {
                                            ContextCompat.checkSelfPermission(
                                                context,
                                                android.Manifest.permission.POST_NOTIFICATIONS
                                            ) -> {
                                                startService()
                                            }
                                            else -> {
                                                launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                                            }
                                        }
                                    }else {
                                        startService()
                                    }
                                },
                                onStopServiceClick = {
                                    stopService()
                                },
                                onSaveClick = { canSave, currency ->
                                    if (canSave) {
                                        viewModel.saveDstCurrency(currency)
                                        viewModel.syncCryptoStats(currency)
                                        Toast.makeText(this, "Changes Saved Successfully!!", Toast.LENGTH_SHORT).show()
                                    }else{
                                        Toast.makeText(this, "First remove old conditions!", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onChangeThemeClick = {
                                    isDarkMode = !isDarkMode
                                },
                                onSelectCrypto = { source, symbol ->
                                    viewModel.getIndicators(
                                        source = source,
                                        symbol = "${symbol}USDT"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun startService() {
        val packageName = packageName
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            Toast.makeText(
                this,
                "Please turn off buttery optimization for this app to prevent stopping service!",
                Toast.LENGTH_LONG
            ).show()
        }
        if (!CryptoAlertService.isServiceStarted) {
            CryptoAlertService.start(this)
        } else {
            Toast.makeText(this, "Service is running!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopService() {
        if (CryptoAlertService.isServiceStarted) {
            CryptoAlertService.stop(this)
        } else {
            Toast.makeText(this, "Service is not running!", Toast.LENGTH_SHORT).show()
        }
    }
}