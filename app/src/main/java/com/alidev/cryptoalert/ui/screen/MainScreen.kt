package com.alidev.cryptoalert.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alidev.cryptoalert.ui.component.BottomTabBar
import com.alidev.cryptoalert.ui.component.Header
import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition
import com.alidev.cryptoalert.ui.viewmodel.stats.CryptoMarketViewModel

@Composable
fun MainScreen(
    cryptos: List<Crypto>,
    cryptoConditions: List<CryptoCondition>,
    dstCurrency: String,
    onAddConditionClick: (CryptoCondition) -> Unit,
    onRemoveConditionClick: (CryptoCondition) -> Unit,
    onStartServiceClick: () -> Unit,
    onStopServiceClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedTabIndex by remember {
        mutableStateOf(1)
    }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1E1E)),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Header()

            when (selectedTabIndex) {
                0 -> {}
                1 -> {
                    CryptoStatsScreen(
                        cryptos = cryptos,
                        dstCurrency = dstCurrency,
                        onAddClick = onAddConditionClick
                    )
                }
                2 -> {
                    CryptoServiceScreen(
                        conditions = cryptoConditions,
                        onStartServiceClick = onStartServiceClick,
                        onStopServiceClick = onStopServiceClick,
                        onRemoveConditionClick = onRemoveConditionClick
                    )
                }
            }
        }

        BottomTabBar(
            selectedTabIndex = selectedTabIndex,
            onFirstTabClick = {
                selectedTabIndex = 0
            },
            onSecondTabClick = {
                selectedTabIndex = 1
            },
            onThirdTabClick = {
                selectedTabIndex = 2
            },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }


}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(
        cryptos = CryptoMarketViewModel.getListOfAvailableCryptos(),
        cryptoConditions = emptyList(),
        dstCurrency = "rls",
        onAddConditionClick = {},
        onStartServiceClick = {},
        onStopServiceClick = {},
        onRemoveConditionClick = {}
    )
}