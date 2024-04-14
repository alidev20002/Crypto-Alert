package com.alidev.cryptoalert.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alidev.cryptoalert.R

@Composable
fun Header(
    modifier: Modifier = Modifier
) {

    var isDarkMode by rememberSaveable {
        mutableStateOf(true)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                ) {

                }
                .padding(4.dp),
            painter = painterResource(id = R.drawable.setting_icon),
            contentDescription = "",
            tint = Color(0xFFAFAEAE)
        )

        Text(
            text = "Crypto Alert",
            color = Color(0xFFAFAEAE),
            fontSize = 24.sp,
            fontWeight = FontWeight(900)
        )

        val themeModeIcon =
            if (isDarkMode) R.drawable.day_icon else R.drawable.night_icon
        Icon(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                ) {
                    isDarkMode = !isDarkMode
                }
                .padding(4.dp),
            painter = painterResource(id = themeModeIcon),
            contentDescription = "",
            tint = Color(0xFFAFAEAE)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    Header()
}