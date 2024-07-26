package com.alidev.cryptoalert.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun CryptoAlertDialog(
    icon: Painter,
    title: String,
    message: String,
    submitButtonText: String,
    cancelButtonText: String,
    onDismiss: () -> Unit,
    onSubmitClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = icon,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.error
            )

            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                text = title,
                color = MaterialTheme.colorScheme.error,
                fontSize = 16.sp,
                fontWeight = FontWeight(700)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier,
                text = message,
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
                            onCancelClick()
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    text = cancelButtonText,
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
                            onSubmitClick()
                        }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    text = submitButtonText,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                )
            }
        }
    }
}