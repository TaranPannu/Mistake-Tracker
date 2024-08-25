package com.example.mistaketracker.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.mistaketracker.DataClass.User
import com.example.mistaketracker.MVVM.MistakeViewModel
import com.example.mistaketracker.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    @Inject
    lateinit var mistakeViewModel: MistakeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpScreen()
        }
    }

    private fun SignUp(username: String, password: String, email: String) {
        if(!(username=="" || password == "" || email == "")) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = mistakeViewModel.RegisterUser(
                        User(
                            username,
                            email,
                            username + "Firstname",
                            username + "Lastname",
                            password
                        )
                    )
                    withContext(Dispatchers.Main) {
                        if (response.body()?.httpStatus == "200") {
                            mistakeViewModel.saveJwtToken(response.body()?.jwt.toString())
                            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@SignUpActivity,
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.d("s3", e.toString())
                }

            }
        }
        else
        {
            Toast.makeText(this@SignUpActivity, "Enter Required Details", Toast.LENGTH_SHORT).show()

        }
    }

    @Composable
    fun SignUpScreen() {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }

        val colors = listOf(
            Color(0xFFF5919C9),
            Color(0xFFF281943),
            Color(0xFFF0A080E)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(brush = Brush.verticalGradient(colors)),
//            verticalArrangement = Arrangement.,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(230.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        "Enter Email",
                        Modifier.padding(bottom = 14.dp, start = 8.dp),
                        fontSize = 17.sp

                    )
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))

            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                label = {
                    Text(
                        "Enter Username",
                        Modifier.padding(bottom = 14.dp, start = 8.dp),
                        fontSize = 17.sp
                    )
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        "Enter Password",
                        Modifier.padding(bottom = 14.dp, start = 8.dp),
                        fontSize = 17.sp
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
                    SignUp(username, password, email)
                },
                shape = RoundedCornerShape(16.dp),//CircleShape,
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF50387C),
                    contentColor = Color.White
                )
            )
            {
                Text("Sign Up", fontSize = 15.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Already have account? Login",
                fontSize = 13.sp,
                color = Color.White,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } // Handle click event
                    .padding(8.dp) // Optional padding for better touch target
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        SignUpScreen()
    }
}

