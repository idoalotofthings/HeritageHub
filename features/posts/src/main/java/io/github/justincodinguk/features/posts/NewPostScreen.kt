package io.github.justincodinguk.features.posts

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.ui.common.IconText
import io.github.justincodinguk.core.ui.posts.ImageUploadPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostScreen(
    modifier: Modifier = Modifier,
    viewModel: PostsViewModel = hiltViewModel(),
    navigateBackToHomeScreen: () -> Unit,
) {

    val state by viewModel.postCreationState.collectAsState()

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) {
        viewModel.updatePostCreationState(photos = it.map { e -> e.toString() })
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = "New Post") }) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.updatePostCreationState(title = it) },
                modifier = Modifier.padding(16.dp),
                label = { Text(text = "Title") },
                singleLine = true
            )

            OutlinedTextField(
                value = state.body,
                onValueChange = { viewModel.updatePostCreationState(body = it) },
                modifier = Modifier
                    .padding(16.dp)
                    .height(256.dp),
                label = { Text(text = "Body") },
            )

            ImageUploadPreview(
                onImageUpload = {
                    galleryLauncher.launch("image/*")
                },
                imageUris = state.photos.map { Uri.parse(it) },
                modifier = Modifier.padding(16.dp)
            )

            ElevatedButton(
                onClick = {
                    viewModel.createNewPost()
                    navigateBackToHomeScreen()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                IconText(icon = Icons.Default.Done, text = "Submit")
            }
        }
    }
}