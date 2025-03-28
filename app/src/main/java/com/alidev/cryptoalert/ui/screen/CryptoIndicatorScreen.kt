package com.alidev.cryptoalert.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.alidev.cryptoalert.ui.model.Crypto

@Composable
fun CryptoIndicatorScreen(
    cryptos: List<Crypto>,
    indicators: Map<String, String>,
    onSelectCrypto: (source: String, symbol: String) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedCrypto by remember {
        mutableStateOf(cryptos[0])
    }

    var selectedSource by remember {
        mutableStateOf("close")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = { expanded = true },
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(36.dp),
                    painter = painterResource(id = selectedCrypto.icon),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    text = selectedCrypto.shortName,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val closeColor = if (selectedSource == "close")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.secondary
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple()
                    ) {
                        selectedSource = "close"
                    }
                    .padding(8.dp),
                text = "CLOSE",
                color = closeColor,
                fontSize = 14.sp,
                fontWeight = FontWeight(700)
            )

            val openColor = if (selectedSource == "open")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.secondary
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple()
                    ) {
                        selectedSource = "open"
                    }
                    .padding(8.dp),
                text = "OPEN",
                color = openColor,
                fontSize = 14.sp,
                fontWeight = FontWeight(700)
            )

            val highColor = if (selectedSource == "high")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.secondary
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple()
                    ) {
                        selectedSource = "high"
                    }
                    .padding(8.dp),
                text = "HIGH",
                color = highColor,
                fontSize = 14.sp,
                fontWeight = FontWeight(700)
            )

            val lowColor = if (selectedSource == "low")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.secondary
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple()
                    ) {
                        selectedSource = "low"
                    }
                    .padding(8.dp),
                text = "LOW",
                color = lowColor,
                fontSize = 14.sp,
                fontWeight = FontWeight(700)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            items(indicators.entries.toList()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = it.key,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight(700)
                    )

                    Text(
                        text = it.value,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight(300)
                    )

                }
            }
        }
    }

    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    text = "Select Crypto",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {

                    items(cryptos) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = rememberRipple()
                                ) {
                                    if (it.shortName != "USDT") {
                                        selectedCrypto = it
                                        expanded = false
                                        onSelectCrypto(selectedSource, it.shortName)
                                    }
                                }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                modifier = Modifier
                                    .size(48.dp),
                                painter = painterResource(id = it.icon),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds
                            )

                            Text(
                                text = it.shortName,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }
                }
            }
        }
    }
}