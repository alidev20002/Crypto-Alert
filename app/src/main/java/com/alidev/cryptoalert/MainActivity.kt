package com.alidev.cryptoalert

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
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

            val isDarkMode = viewModel.isDarkMode.collectAsState().value

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
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                MainScreen(
                                    cryptos = state.cryptos,
                                    cryptoConditions = state.cryptoConditions,
                                    indicators = state.indicators,
                                    dstCurrency = state.dstCurrency,
                                    isDarkMode = isDarkMode,
                                    onAddConditionClick = {
                                        if (!CryptoAlertService.isServiceStarted) {
                                            viewModel.addCondition(it)
                                            Toast.makeText(this, "Condition added successfully!", Toast.LENGTH_SHORT).show()
                                        }else {
                                            Toast.makeText(this, "When Service is running, you cannot modify conditions!", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    onRemoveConditionClick = {
                                        if (!CryptoAlertService.isServiceStarted) {
                                            viewModel.removeCondition(it)
                                            Toast.makeText(this, "Condition removed successfully!", Toast.LENGTH_SHORT).show()
                                        }else {
                                            Toast.makeText(this, "When Service is running, you cannot modify conditions!", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    onRemoveAllConditionsClick = {
                                        if (!CryptoAlertService.isServiceStarted) {
                                            viewModel.removeAllConditions()
                                            Toast.makeText(this, "Conditions removed successfully!", Toast.LENGTH_SHORT).show()
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
                                        viewModel.setTheme(!isDarkMode)
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
    }

    private fun startService() {
        requestIgnoreBatteryOptimizationIfRequired()
        if (!CryptoAlertService.isServiceStarted) {
            CryptoAlertService.start(this)
        } else {
            Toast.makeText(this, "Service is running!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestIgnoreBatteryOptimizationIfRequired() {
        val packageName = packageName
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            Toast.makeText(
                this,
                "Please turn off buttery optimization for this app to prevent stopping service!",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent()
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
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