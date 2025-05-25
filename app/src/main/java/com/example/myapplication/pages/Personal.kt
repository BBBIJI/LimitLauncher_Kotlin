package com.example.myapplication.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.AppViewModel

@Composable
fun Personal(
    navController: NavHostController,
    viewModel: AppViewModel,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("First Name", modifier = Modifier.padding(horizontal = 32.dp, vertical = 10.dp))
        PersonalInputField("First name")
        Text("Last Name", modifier = Modifier.padding(horizontal = 32.dp, vertical = 10.dp))
        PersonalInputField("Last Name")
        Text("E-mail", modifier = Modifier.padding(horizontal = 32.dp, vertical = 10.dp))
        PersonalInputField("E-mail")
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
fun PersonalInputField(
    placeholder: String,
    isPassword: Boolean = false
) {
    var text by remember { mutableStateOf("") }
    val visualTransformation =
        if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

    Row(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = placeholder) },
            visualTransformation = visualTransformation,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
            ),
        )
    }
}