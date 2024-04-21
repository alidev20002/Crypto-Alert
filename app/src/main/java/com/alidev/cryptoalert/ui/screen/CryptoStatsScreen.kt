package com.alidev.cryptoalert.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.alidev.cryptoalert.R
import com.alidev.cryptoalert.ui.model.Condition
import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition
import com.alidev.cryptoalert.ui.transformation.PriceVisualTransformation
import com.alidev.cryptoalert.ui.viewmodel.stats.CryptoMarketViewModel
import com.alidev.cryptoalert.utils.toFormattedPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoStatsScreen(
    cryptos: List<Crypto>,
    onAddClick: (CryptoCondition) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedCrypto by remember {
        mutableStateOf(cryptos[0])
    }
    
    var expectedPrice by remember {
        mutableStateOf(cryptos[0].latestPrice)
    }

    var condition by remember {
        mutableStateOf(Condition.INCREASE)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Spacer(modifier = Modifier.width(0.dp))
            }

            items(cryptos) {

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .width(120.dp)
                        .padding(10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = it.shortName,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        val isChangePositive = it.change.toDouble() >= 0
                        val changeColor = if (isChangePositive) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.error
                        val changeText = if (isChangePositive) "+${it.change}" else it.change
                        Text(
                            text = changeText,
                            color = changeColor
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = it.latestPrice.toFormattedPrice(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight(300)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Image(
                        painter = painterResource(id = it.icon),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.width(0.dp))
            }
        }

        Column(
            modifier = Modifier
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

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0x00000000))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp),
                value = expectedPrice,
                onValueChange = {
                    if (it.isDigitsOnly())
                        expectedPrice = it
                },
                singleLine = true,
                maxLines = 1,
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500)
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified,
                    textColor = MaterialTheme.colorScheme.onBackground
                ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple()
                            ) {
                                expectedPrice = (expectedPrice.toDouble() - 100)
                                    .toBigDecimal()
                                    .toPlainString()
                            }
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.minus_icon),
                        contentDescription = ""
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple()
                            ) {
                                expectedPrice = (expectedPrice.toDouble() + 100)
                                    .toBigDecimal()
                                    .toPlainString()
                            }
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.plus_icon),
                        contentDescription = ""
                    )
                },
                visualTransformation = PriceVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                val increaseBackgroundColor = if (condition == Condition.INCREASE)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary

                val decreaseBackgroundColor = if (condition == Condition.INCREASE)
                    MaterialTheme.colorScheme.secondary
                else
                    MaterialTheme.colorScheme.primary

                Button(
                    shape = RoundedCornerShape(16.dp, 0.dp, 0.dp, 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = increaseBackgroundColor
                    ),
                    onClick = {
                        condition = Condition.INCREASE
                    }
                ) {

                    Text(
                        text = "Increase",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                }

                Button(
                    shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = decreaseBackgroundColor
                    ),
                    onClick = {
                        condition = Condition.DECREASE
                    }
                ) {
                    Text(
                        text = "Decrease",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    onAddClick(
                        CryptoCondition(
                            crypto = selectedCrypto,
                            expectedPrice = expectedPrice.toDouble(),
                            condition = condition
                        )
                    )
                }
            ) {
                Text(
                    text = "Add Condition",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700)
                )
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
                                    selectedCrypto = it
                                    expectedPrice = it.latestPrice
                                    expanded = false
                                }
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
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


@Composable
@Preview(showBackground = true)
private fun CryptoStatsScreenPreview() {
    CryptoStatsScreen(
        cryptos = CryptoMarketViewModel.getListOfAvailableCryptos(),
        onAddClick = {}
    )
}