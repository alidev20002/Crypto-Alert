package com.alidev.cryptoalert.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CryptoSettingScreen(
    dstCurrency: String,
    canSaveDstCurrency: Boolean,
    onSaveClick: (Boolean, String) -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedDstCurrency by remember {
        mutableStateOf(dstCurrency)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = "Destination Currency",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight(700)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            val usdtColor = if (selectedDstCurrency == "usdt") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            Button(
                modifier = Modifier
                    .height(42.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    selectedDstCurrency = "usdt"
                }
            ) {
                Text(
                    text = "USDT",
                    color = usdtColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight(900)
                )
            }

            val tomanColor = if (selectedDstCurrency == "rls") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            Button(
                modifier = Modifier
                    .height(42.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = {
                    selectedDstCurrency = "rls"
                }
            ) {
                Text(
                    text = "TOMAN",
                    color = tomanColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight(900)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                onSaveClick(canSaveDstCurrency, selectedDstCurrency)
            }
        ) {
            Text(
                text = "Save Changes",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight(700)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CryptoSettingScreenPreview() {
    CryptoSettingScreen(
        dstCurrency = "rls",
        canSaveDstCurrency = false,
        onSaveClick = { _, _ -> }
    )
}