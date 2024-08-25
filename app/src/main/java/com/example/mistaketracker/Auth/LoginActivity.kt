package com.example.mistaketracker.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.mistaketracker.MVVM.MistakeViewModel
import com.example.mistaketracker.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    @Inject
    lateinit var mistakeViewModel: MistakeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }

    private fun Login(username: String, password: String) {
        if (!(username == "" || password == "")) {
            lifecycleScope.launch(Dispatchers.IO) {// mistakeViewModel.getDetails()
                val response = mistakeViewModel.Loginuser(
                    com.example.mistaketracker.DataClass.Login(
                        username, password
                    )
                )
                withContext(Dispatchers.Main) {
                    if (response.body()?.httpStatus == "200") {
                        mistakeViewModel.saveJwtToken(response.body()?.jwt.toString())
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity, response.body()?.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Toast.makeText(this@LoginActivity, "Enter Required Details", Toast.LENGTH_SHORT).show()

        }
    }

    @Composable
    fun LoginScreen() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val colors = listOf(
            Color(0xFFF5919C9), Color(0xFFF281943), Color(0xFFF0A080E)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(brush = Brush.verticalGradient(colors)),
//            verticalArrangement = Arrangement.,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(280.dp))
            TextField(value = username,
                onValueChange = { username = it },
                label = {
                    Text(
                        "username", Modifier.padding(bottom = 14.dp, start = 8.dp), fontSize = 17.sp
                    )
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        "password", Modifier.padding(bottom = 14.dp, start = 8.dp), fontSize = 17.sp
                    )
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            val buttonModifier = Modifier
                .width(100.dp)
                .height(35.dp)
//                .clip(RoundedCornerShape(25.dp)).border(BorderStroke(width = 2.dp, color = Color.Blue))
            Button(
                onClick = {
                    Login(username, password)
                }, shape = RoundedCornerShape(16.dp),//CircleShape,
                modifier = buttonModifier, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF50387C), contentColor = Color.White
                )
            ) {
                Text("Login", fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Don't have account? Sign Up",
                fontSize = 13.sp,
                color = Color.White,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                        startActivity(intent)
                    } // Handle click event
                    .padding(8.dp) // Optional padding for better touch target
            )


        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        LoginScreen()
    }
}

