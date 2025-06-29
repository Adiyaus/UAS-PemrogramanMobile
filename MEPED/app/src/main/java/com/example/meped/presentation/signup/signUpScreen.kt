package com.example.meped.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meped.R

@Composable
fun SignUpScreen(
    onSignInClick: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "MP", fontSize = 80.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0016C3))
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Silahkan buat akun anda",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Email") }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(30.dp))
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(30.dp))
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Konfirmasi Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(30.dp))
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { onSignUpSuccess() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(text = "Buat Akun")
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "-Atau buat akun dengan-", color = Color.Gray)
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(painter = painterResource(id = R.drawable.google), contentDescription = "Google", modifier = Modifier.size(50.dp).clickable { })
            Image(painter = painterResource(id = R.drawable.fb), contentDescription = "Facebook", modifier = Modifier.size(50.dp).clickable { })
            Image(painter = painterResource(id = R.drawable.x), contentDescription = "Twitter", modifier = Modifier.size(50.dp).clickable { })
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row {
            Text(text = "Sudah punya akun? ")
            Text(
                text = "Sign In",
                color = Color(0xFF0016C3),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onSignInClick() }
            )
        }
    }
}