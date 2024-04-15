package com.alidev.cryptoalert.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alidev.cryptoalert.R

@Composable
fun BottomTabBar(
    selectedTabIndex: Int,
    onFirstTabClick: () -> Unit,
    onSecondTabClick: () -> Unit,
    onThirdTabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = Color(0xFF9142FF),
                shape = RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .weight(1F)
                .height(60.dp)
                .clip(RoundedCornerShape(32.dp, 0.dp, 0.dp, 0.dp))
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                ) {
                    onFirstTabClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.speedometer_icon),
                contentDescription = "",
                tint = if (selectedTabIndex == 0) Color(0xFF383636) else Color(0xFFFFFFFF)
            )
        }


        Box(
            modifier = Modifier
                .weight(1F)
                .height(60.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                ) {
                    onSecondTabClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.home_icon),
                contentDescription = "",
                tint = if (selectedTabIndex == 1) Color(0xFF383636) else Color(0xFFFFFFFF)
            )
        }

        Box(
            modifier = Modifier
                .weight(1F)
                .height(60.dp)
                .clip(RoundedCornerShape(0.dp, 32.dp, 0.dp, 0.dp))
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                ) {
                    onThirdTabClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.notification_alert_icon),
                contentDescription = "",
                tint = if (selectedTabIndex == 2) Color(0xFF383636) else Color(0xFFFFFFFF)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomTabBarPreview() {
    BottomTabBar(
        selectedTabIndex = 1,
        onFirstTabClick = {},
        onSecondTabClick = {},
        onThirdTabClick = {}
    )
}