package com.alidev.cryptoalert.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.alidev.cryptoalert.R
import com.alidev.cryptoalert.data.api.getCryptoIcon
import com.alidev.cryptoalert.ui.model.Condition
import com.alidev.cryptoalert.ui.model.Crypto
import com.alidev.cryptoalert.ui.model.CryptoCondition

@Composable
fun CryptoServiceScreen(
    conditions: List<CryptoCondition>,
    onStartServiceClick: () -> Unit,
    onStopServiceClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){

            Button(
                modifier = Modifier
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9142FF)
                ),
                onClick = onStartServiceClick
            ) {
                Text(
                    text = "Start Service",
                    color = Color(0xFFFFFFFF),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                )
            }

            Button(
                modifier = Modifier
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9142FF)
                ),
                onClick = onStopServiceClick
            ) {
                Text(
                    text = "Stop Service",
                    color = Color(0xFFFFFFFF),
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

            items(conditions) {
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(12.dp),
                            ambientColor = Color(0xFF000000)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple()
                        ) {

                        }
                        .background(Color(0xFF272727))
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
                            color = Color(0xFFFFFFFF)
                        )

                        val conditionText = if (it.condition == Condition.INCREASE) "Greater Than " else "Less Than "
                        Text(
                            text = "$conditionText ${it.expectedPrice}",
                            color = Color(0xFFFFFFFF)
                        )
                    }

                    val conditionIcon =
                        if (it.condition == Condition.INCREASE) R.drawable.top_arrow_icon else R.drawable.bottom_arrow_icon

                    val conditionColor =
                        if (it.condition == Condition.INCREASE) Color(0xFF30E0A1) else Color(0xFFE6445D)


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
                Spacer(modifier = Modifier.height(52.dp))
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
        onStopServiceClick = {}
    )
}