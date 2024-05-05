package io.github.justincodinguk.core.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.justincodinguk.core.ui.theme.HeritageHubTheme

@Composable
fun CredentialsTextField(
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(),
        modifier = modifier.padding(4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun CredentialsTextFieldPreview() {
    HeritageHubTheme(dynamicColor = false) {
        CredentialsTextField(value = "", onValueChange = {}, label = "Email")
    }
}