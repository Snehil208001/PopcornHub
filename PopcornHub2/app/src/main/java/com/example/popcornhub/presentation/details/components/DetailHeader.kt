package com.example.popcornhub.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.popcornhub.data.model.WatchContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun DetailHeader(content:WatchContent){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        AsyncImage(
            model = content.posterUrl,
            contentDescription = content.description,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )

        Text(text = content.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
            )
        Text(text = content.releaseDate?: "Release year Not Available",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
    }


}