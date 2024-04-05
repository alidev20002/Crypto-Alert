package com.alidev.cryptoalert

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alidev.cryptoalert.data.api.getCryptoIcon
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

                    var conditionId by rememberSaveable {
                        mutableStateOf(1.0)
                    }

                    var isRial by rememberSaveable {
                        mutableStateOf(true)
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

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                ) {
                                    items(conditions) {
                                        Row(
                                            modifier = Modifier
                                                .combinedClickable(
                                                    onLongClick = {
                                                        if (!CryptoAlertService.isServiceStarted) {
                                                            viewModel.removeCondition(it)
                                                        }else {
                                                            Toast.makeText(this@MainActivity, "When Service is running, you cannot modify conditions!", Toast.LENGTH_SHORT).show()
                                                        }
                                                    },
                                                    onClick = {}
                                                ),
                                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = it.crypto.icon),
                                                contentDescription = "",
                                                contentScale = ContentScale.FillBounds
                                            )
                                            Text(text = it.crypto.shortName)
                                            Text(text = "${it.expectedPrice}")
                                            Text(text = it.condition.name)
                                        }
                                    }
                                }

                                Button(
                                    onClick = {
                                        if (!CryptoAlertService.isServiceStarted) {
                                            viewModel.addCondition(
                                                CryptoCondition(
                                                    Crypto(
                                                        shortName = "btc",
                                                        icon = getCryptoIcon("btc")
                                                    ),
                                                    40000000000.0 + conditionId * 1500000000,
                                                    Condition.INCREASE
                                                )
                                            )
                                            conditionId += 1.0
                                        }else {
                                            Toast.makeText(this@MainActivity, "When Service is running, you cannot modify conditions!", Toast.LENGTH_SHORT).show()
                                        }

                                    }
                                ) {
                                    Text(text = "Add new Condition")
                                }
                                
                                Spacer(modifier = Modifier.height(20.dp))

                                val stats = state.cryptos
                                isRial = state.dstCurrency == "rls"

                                Row {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if(isRial) Color.Cyan else Color.Red
                                        ),
                                        onClick = {
                                            if (conditions.isEmpty()) {
                                                viewModel.saveDstCurrency("usdt")
                                                viewModel.syncCryptoStats("usdt")
                                            }else {
                                                Toast.makeText(this@MainActivity, "Please remove conditions first!", Toast.LENGTH_SHORT).show()
                                            }

                                        }
                                    ) {
                                        Text(text = "USDT")
                                    }

                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if(!isRial) Color.Cyan else Color.Red
                                        ),
                                        onClick = {
                                            if (conditions.isEmpty()) {
                                                viewModel.saveDstCurrency("rls")
                                                viewModel.syncCryptoStats("rls")
                                            }else {
                                                Toast.makeText(this@MainActivity, "Please remove conditions first!", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    ) {
                                        Text(text = "RLS")
                                    }
                                }
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    
                                    items(stats) {
                                        Column(
                                            modifier = Modifier
                                                .background(Color.Gray, RoundedCornerShape(15.dp))
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Image(
                                                painter = painterResource(id = it.icon),
                                                contentDescription = "",
                                                contentScale = ContentScale.FillBounds
                                            )
                                            Text(text = it.latestPrice)
                                            Text(text = it.shortName)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}