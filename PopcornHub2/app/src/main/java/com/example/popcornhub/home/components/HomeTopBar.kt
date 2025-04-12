package com.example.popcornhub.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.popcornhub.utils.ContentType

@Composable
fun HomeTopBar(
    selectedType: ContentType,
    onTypeSelected: (ContentType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 28.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ContentType.values().forEach { type ->
            Button(
                onClick = { onTypeSelected(type) }, // ✅ Fixed here only
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedType == type)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selectedType == type)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = type.name.uppercase(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
