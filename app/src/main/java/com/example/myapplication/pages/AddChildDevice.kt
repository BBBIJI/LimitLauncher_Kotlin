package com.example.myapplication.pages

import android.text.Html.ImageGetter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.navigation.Screens

@Composable
fun AddChildDevice(
    paddingvalues: PaddingValues,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(paddingvalues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text("Add Your Child's Device:", fontSize = 20.sp)
        Image(
            painter = painterResource(R.drawable.asset_1_0),
            contentDescription = null
        )
        Button(
            onClick = {},
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text("Scan QR Code")
        }
    }
}