package com.alidev.cryptoalert.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.alidev.cryptoalert.R
import com.alidev.cryptoalert.data.api.getCryptoIcon
import com.alidev.cryptoalert.ui.model.Condition
import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition
import com.alidev.cryptoalert.utils.toFormattedPrice

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CryptoServiceScreen(
    conditions: List<CryptoCondition>,
    onStartServiceClick: () -> Unit,
    onStopServiceClick: () -> Unit,
    onRemoveConditionClick: (CryptoCondition) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedCrypto by remember {
        mutableStateOf<CryptoCondition?>(null)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){

            val isStartButtonEnabled = conditions.isNotEmpty()
            Button(
                modifier = Modifier
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary
                ),
                enabled = isStartButtonEnabled,
                onClick = onStartServiceClick
            ) {
                Text(
                    text = "Start Service",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                )
            }

            Button(
                modifier = Modifier
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = onStopServiceClick
            ) {
                Text(
                    text = "Stop Service",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(conditions) {
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(12.dp),
                            ambientColor = Color(0xFF000000)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .combinedClickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(),
                            onLongClick = {
                                selectedCrypto = it
                                expanded = true
                            },
                            onClick = {}
                        )
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Image(
                        modifier = Modifier
                            .size(36.dp),
                        painter = painterResource(id = it.crypto.icon),
                        contentDescription = "",
                    )

                    Column(
                        modifier = Modifier
                            .weight(0.9F),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        Text(
                            text = it.crypto.shortName,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        val conditionText =
                            if (it.condition == Condition.INCREASE) "Greater Than " else "Less Than "
                        Text(
                            text = "$conditionText ${it.expectedPrice
                                .toBigDecimal().toPlainString().toFormattedPrice()}",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    val conditionIcon =
                        if (it.condition == Condition.INCREASE) R.drawable.top_arrow_icon else R.drawable.bottom_arrow_icon

                    val conditionColor =
                        if (it.condition == Condition.INCREASE) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.error


                    Icon(
                        modifier = Modifier
                            .size(16.dp),
                        painter = painterResource(id = conditionIcon),
                        contentDescription = "",
                        tint = conditionColor
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(62.dp))
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
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.recycle_bin_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.error
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    text = "Remove Condition",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier,
                    text = "Are you sure you want to remove this item from the list?",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(300)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple()
                            ) {
                                expanded = false
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        text = "NO",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    )

                    Text(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple()
                            ) {
                                selectedCrypto?.let {
                                    onRemoveConditionClick(it)
                                }
                                expanded = false
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        text = "YES",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CryptoServiceScreenPreview() {
    CryptoServiceScreen(
        conditions = listOf(
            CryptoCondition(
                Crypto(
                    shortName = "BTC",
                    icon = getCryptoIcon("btc")
                ),
                4387.0,
                Condition.INCREASE
            ),
            CryptoCondition(
                Crypto(
                    shortName = "BTC",
                    icon = getCryptoIcon("btc")
                ),
                4387.0,
                Condition.DECREASE
            )
        ),
        onStartServiceClick = {},
        onStopServiceClick = {},
        onRemoveConditionClick = {}
    )
}