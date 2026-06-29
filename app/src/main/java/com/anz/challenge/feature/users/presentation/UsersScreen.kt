package com.anz.challenge.feature.users.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.anz.challenge.R
import com.anz.challenge.common.AppError
import com.anz.challenge.feature.users.domain.models.User

@Composable
fun UsersListScreen(
    viewModel: UsersViewModel = hiltViewModel(),
    onUserClick: (User) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    UsersScreenContent(
        state = state,
        onRetry = { viewModel.getUsers() },
        onUserClick = onUserClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreenContent(
    state: UsersUiState,
    onRetry: () -> Unit,
    onUserClick: (User) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background, topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.screen_title_users),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }) { padding ->

        when (state) {

            UsersUiState.Loading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .testTag("loading_indicator"),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UsersUiState.Success -> {

                if (state.users.isEmpty()) {
                    EmptyState()
                } else {
                    PostList(
                        users = state.users,
                        modifier = Modifier.padding(padding),
                        onUserClick = onUserClick
                    )
                }
            }

            is UsersUiState.Error -> {

                ErrorState(
                    error = state.error, modifier = Modifier.padding(padding), onRetry = onRetry
                )
            }
        }
    }
}

@Composable
fun PostList(
    users: List<User>,
    modifier: Modifier = Modifier,
    onUserClick: (User) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("post_list")
    ) {
        itemsIndexed(
            items = users, key = { _, user -> user.id }) { index, user ->

            PostItem(
                user = user,
                onUserClick = onUserClick
            )

            if (index < users.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun PostItem(
    user: User,
    modifier: Modifier = Modifier,
    onUserClick: (User) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onUserClick(user) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PostAvatar(
            imageUrl = user.photo,
            contentDescription = user.name
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PostAvatar(
    imageUrl: String?,
    contentDescription: String?
) {
    val placeholderPainter = rememberVectorPainter(
        image = Icons.Default.AccountCircle
    )
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        placeholder = placeholderPainter,
        error = placeholderPainter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
    )
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("empty_state"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_posts_available),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ErrorState(
    error: AppError, modifier: Modifier = Modifier, onRetry: () -> Unit
) {
    val message = when (error) {
        AppError.NoInternet -> stringResource(R.string.error_no_internet)

        AppError.Unauthorized -> stringResource(R.string.error_unauthorized)

        AppError.ServerError -> stringResource(R.string.error_generic)

        is AppError.Unknown -> error.message?.takeIf { it.isNotBlank() }
            ?: stringResource(R.string.error_generic)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            modifier = Modifier.testTag("error_message"),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        if (error is AppError.NoInternet) {

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRetry, modifier = Modifier.testTag("retry_button")
            ) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}