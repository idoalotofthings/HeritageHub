package io.github.justincodinguk.features.heritage

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import io.github.justincodinguk.core.ui.heritage.HeritageTree

@Composable
fun MyHeritageScreen(
    modifier: Modifier = Modifier,
    userId: String,
    viewModel: HeritageViewModel = hiltViewModel(),
    navigateToHeritageCreationScreen: () -> Unit,
) {

    viewModel.loadHeritageFor(userId)
    val state by viewModel.userHeritage.collectAsState()

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
        if(state.heritageElements.isNotEmpty()){
            HeritageTree(
                heritage = state,
                modifier = Modifier.padding(innerPadding)
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