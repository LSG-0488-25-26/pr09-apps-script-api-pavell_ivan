package com.example.dataset.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatSlider(
    label:    String,
    value:    Float,
    onChange: (Float) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(
                value.toInt().toString(),
                fontSize   = 14.sp,
                fontWeight = FontWeight.Bold,
                color      = when {
                    value < 50f  -> MaterialTheme.colorScheme.error
                    value < 100f -> MaterialTheme.colorScheme.onSurface
                    else         -> MaterialTheme.colorScheme.primary
                }
            )
        }
        Slider(
            value         = value,
            onValueChange = onChange,
            valueRange    = 1f..255f,
            modifier      = Modifier.fillMaxWidth()
        )
    }
}