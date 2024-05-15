package com.alidev.cryptoalert.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alidev.cryptoalert.ui.component.BottomTabBar
import com.alidev.cryptoalert.ui.component.Header
import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition
import com.alidev.cryptoalert.ui.viewmodel.CryptoMarketViewModel

@Composable
fun MainScreen(
    cryptos: List<Crypto>,
    cryptoConditions: List<CryptoCondition>,
    indicators: Map<String, String>,
    dstCurrency: String,
    isDarkMode: Boolean,
    onAddConditionClick: (CryptoCondition) -> Unit,
    onRemoveConditionClick: (CryptoCondition) -> Unit,
    onStartServiceClick: () -> Unit,
    onStopServiceClick: () -> Unit,
    onSaveClick: (Boolean, String) -> Unit,
    onChangeThemeClick: () -> Unit,
    onSelectCrypto: (String) -> Unit,
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
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Header(
                isDarkMode = isDarkMode,
                onSettingsClick = {
                    selectedTabIndex = 3
                },
                onChangeThemeClick = onChangeThemeClick
            )

            when (selectedTabIndex) {
                0 -> {
                    CryptoIndicatorScreen(
                        cryptos = cryptos,
                        indicators = indicators,
                        onSelectCrypto = onSelectCrypto
                    )
                }
                1 -> {
                    CryptoStatsScreen(
                        cryptos = cryptos,
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
                3 -> {
                    CryptoSettingScreen(
                        dstCurrency = dstCurrency,
                        canSaveDstCurrency = cryptoConditions.isEmpty(),
                        onSaveClick = onSaveClick
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
        indicators = emptyMap(),
        dstCurrency = "rls",
        isDarkMode = false,
        onAddConditionClick = {},
        onStartServiceClick = {},
        onStopServiceClick = {},
        onRemoveConditionClick = {},
        onSaveClick = { _, _ -> },
        onChangeThemeClick = {},
        onSelectCrypto = {}
    )
}