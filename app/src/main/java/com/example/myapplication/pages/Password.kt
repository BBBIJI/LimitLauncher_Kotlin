package com.example.myapplication.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel


@Composable
fun Password(
    navController: NavHostController,
    viewModel: AppViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Old Password", modifier = Modifier.padding(start = 32.dp, top = 10.dp, end = 32.dp))
        PasswordInputField("First name")
        Spacer(modifier = Modifier.height(20.dp))
        Text("New Password", modifier = Modifier.padding(start = 32.dp, top = 10.dp, end = 32.dp))
        PasswordInputField("Last Name")
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Confirm Password",
            modifier = Modifier.padding(start = 32.dp, top = 10.dp, end = 32.dp)
        )
        PasswordInputField("E-mail")
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Text(
                "Biometric Login",
                modifier = Modifier.padding(start = 32.dp, top = 10.dp, end = 32.dp)
            )
            Row(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
            ) {
                Text(
                    "Enable for added security",
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                var checked by remember { mutableStateOf(false) }
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    },
                    modifier = Modifier,
                    thumbContent = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Button(
            onClick = {}, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Update")
        }
    }
}

@Composable
fun PasswordInputField(
    placeholder: String,
    isPassword: Boolean = false
) {
    var text by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = placeholder) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
    }
}