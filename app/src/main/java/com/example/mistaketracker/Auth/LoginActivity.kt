package com.example.mistaketracker.Auth
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.lifecycle.lifecycleScope
import com.example.mistaketracker.MVVM.MistakeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var mistakeViewModel:MistakeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }

    private fun Login(username: String, password: String) {
//         Perform sign-up logic here
        Log.d("LoginActivity", "Login clicked with username: $username and password: $password")
        lifecycleScope.launch(Dispatchers.IO){
           val response =  mistakeViewModel.Loginuser(
                com.example.mistaketracker.DataClass.Login(
                    username,
                    password
                )
            )
            Log.d("LoginActivity", "Login clicked with ----->: ${response.body()}")

            mistakeViewModel.saveJwtToken(this@LoginActivity, response.body()?.jwt.toString())
        }
        val x= mistakeViewModel.getJwtToken(this@LoginActivity)
        Log.d("LoginActivity", "Login clicked with ----->: $x")
    }

    @Composable
    fun LoginScreen() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Enter your username") },
                modifier = Modifier
                    .width(200.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter your password") },
                modifier = Modifier
                    .width(200.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { Login(username, password) }
            ) {
                Text("LoginUp")
                Modifier
                    .background(Color.White, shape = RoundedCornerShape(25.dp))
                    .width(50.dp)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        LoginScreen()
    }
}

