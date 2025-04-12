package com.example.popcornhub.presentation.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.popcornhub.presentation.details.DetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailScreen(
    contenId: String,
    contentType: String,
    onNavigateBack: () -> Unit,
    viewModel: DetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val isMovie = contentType == "movie"

    LaunchedEffect(state) {
        (state as? DetailState.Error)?.let {
            snackbarHostState.showSnackbar(
                message = it.message,
                duration = SnackbarDuration.Long,
                withDismissAction = true
            )
        }
    }

    LaunchedEffect(contenId) {
        viewModel.loadContent(contenId, isMovie)
    }

    Scaffold(
        topBar = { DetailsTopBar(state, onNavigateBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedContent(
                targetState = state,
                transitionSpec = { fadeIn() with fadeOut() }
            ) { currentState ->
                when (currentState) {
                    is DetailState.Loading -> LoadingIndicator()
                    is DetailState.Success -> DetailContent(
                        content = currentState.content,
                        modifier = Modifier.fillMaxSize()
                    )
                    is DetailState.Error -> ErrorScreen(
                        message = currentState.message,
                        onRetry = {
                            scope.launch {
                                viewModel.loadContent(contenId, isMovie)
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(state: DetailState, onNavigateBack: () -> Unit) {
    LargeTopAppBar(
        title = {
            val title = (state as? DetailState.Success)?.content?.title ?: "Details"
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
fun LoadingIndicator() {
    val alpha by rememberInfiniteTransition().animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    CircularProgressIndicator(
        modifier = Modifier.size(48.dp).alpha(alpha),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRetry) { Text("Retry") }
    }
}
