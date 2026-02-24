package com.example.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.PrimaryColorPink

@Composable
fun CustomSnackbar(
    snackbarData: SnackbarData,
    isError: Boolean = false
) {
    Snackbar(
        modifier = Modifier.padding(16.dp),
        containerColor = if (isError) Color.Red else PrimaryColorPink,
        contentColor = Color.White,
        action = {
            snackbarData.visuals.actionLabel?.let { actionLabel ->
                TextButton(
                    onClick = { snackbarData.performAction() }
                ) {
                    Text(
                        text = actionLabel,
                        color = Color.White
                    )
                }
            }
        }
    ) {
        Text(snackbarData.visuals.message)
    }
}