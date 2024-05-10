package io.github.justincodinguk.features.heritage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import io.github.justincodinguk.core.ui.heritage.HeritageTree
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MyHeritageScreen(
    modifier: Modifier = Modifier,
    userId: String,
    viewModel: HeritageViewModel = hiltViewModel(),
    navigateToHeritageCreationScreen: () -> Unit,
) {

    viewModel.loadHeritageFor(userId)
    val state by viewModel.userHeritage.collectAsState()
    val name by viewModel.username.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = { HeritageHubTopAppBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToHeritageCreationScreen) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            if(state.heritageElements.isNotEmpty()){

                AsyncImage(
                    model = name.profileImage,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .size(96.dp)

                )

                Text(
                    text = "${name.name}'s Heritage",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp),
                    style = MaterialTheme.typography.headlineLarge
                )

                HorizontalDivider(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp))

                HeritageTree(
                    heritage = state,
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                Text(
                    text = "You haven't been tracking your heritage. Let's start by tapping on the edit button",
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                )
            }
        }
    }
}