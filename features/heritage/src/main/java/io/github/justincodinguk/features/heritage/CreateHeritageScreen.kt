package io.github.justincodinguk.features.heritage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.justincodinguk.core.ui.common.CreatePostFloatingActionButton
import io.github.justincodinguk.core.ui.common.HeritageHubTopAppBar
import io.github.justincodinguk.core.ui.heritage.HeritageTree

@Composable
fun CreateHeritageScreen(
    modifier: Modifier = Modifier,
    viewModel: HeritageViewModel = hiltViewModel()
) {

    val state by viewModel.heritageCreationState.collectAsState()
    var dialogVisible by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        topBar = { HeritageHubTopAppBar() },
        floatingActionButton = {
            CreatePostFloatingActionButton(
                onClick = {
                    dialogVisible = true
                }
            )
        }
    ) { innerPadding ->

        HeritageTree(
            heritage = state.heritage,
            modifier = Modifier.padding(innerPadding)
        )

        if (dialogVisible) {
            Dialog(onDismissRequest = {}) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = state.title,
                        onValueChange = {
                            viewModel.updateText(title = it)
                        },
                        label = {
                            Text(text = "Title")
                        },
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(32.dp)
                    )

                    OutlinedTextField(
                        value = state.generation,
                        onValueChange = {
                            viewModel.updateText(generation = it)
                        },
                        label = {
                            Text(text = "Generation")
                        },
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(32.dp)
                    )

                    ElevatedButton(
                        onClick = {
                            viewModel.addHeritageElement()
                            viewModel.updateText("", "")
                            dialogVisible = false
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}