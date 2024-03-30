package com.alidev.cryptoalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alidev.cryptoalert.service.CryptoAlertService
import com.alidev.cryptoalert.ui.model.Condition
import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition
import com.alidev.cryptoalert.ui.theme.CryptoAlertTheme
import com.alidev.cryptoalert.ui.viewmodel.stats.CryptoMarketState
import com.alidev.cryptoalert.ui.viewmodel.stats.CryptoMarketViewModel
import com.alidev.cryptoalert.ui.viewmodel.stats.EmptyMarketState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<CryptoMarketViewModel>()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAlertTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isEnabled by rememberSaveable {
                        mutableStateOf(false)
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Button(
                            enabled = isEnabled,
                            onClick = {
                                CryptoAlertService.start(this@MainActivity)
                            }
                        ) {
                            Text(text = "Start Service")
                        }

                        Button(
                            enabled = isEnabled,
                            onClick = {
                                CryptoAlertService.stop(this@MainActivity)
                            }
                        ) {
                            Text(text = "Stop Service")
                        }

                        val state = viewModel.state.collectAsState().value
                        when (state) {
                            is EmptyMarketState -> {
                                Text(text = "No Condition yet!")
                            }

                            is CryptoMarketState -> {
                                val conditions = state.cryptoConditions
                                isEnabled = conditions.isNotEmpty()

                                LazyColumn {
                                    items(conditions) {
                                        Row(
                                            modifier = Modifier
                                                .combinedClickable(
                                                    onLongClick = {
                                                        viewModel.removeCondition(it)
                                                    },
                                                    onClick = {}
                                                ),
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = it.crypto.icon),
                                                contentDescription = null
                                            )
                                            Text(text = it.crypto.name)
                                            Text(text = "${it.expectedPrice}")
                                            Text(text = it.condition.name)
                                        }
                                    }
                                }

                                Button(
                                    onClick = {
                                        viewModel.addCondition(
                                            CryptoCondition(
                                                Crypto(
                                                    name = "btc",
                                                    icon = R.drawable.ic_launcher_foreground
                                                ),
                                                40000000.0,
                                                Condition.INCREASE
                                            )
                                        )
                                    }
                                ) {
                                    Text(text = "Add new Condition")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}