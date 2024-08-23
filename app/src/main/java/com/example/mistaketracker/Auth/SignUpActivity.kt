package com.example.mistaketracker.Auth
import android.app.Application
import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import com.example.mistaketracker.DataClass.User
import com.example.mistaketracker.MVVM.MistakeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private fun SignUp(username: String, password: String) {
        // Perform sign-up logic here
        Log.d("SignUpActivity", "SignUp clicked with username: $username and password: $password")
       lifecycleScope.launch(Dispatchers.IO) {
           try{
               val response = mistakeViewModel.RegisterUser(
                   User(
                       username,
                       username + "@gmail.com",
                       username + "uuu",
                       password + "kkk",
                       password
                   )
               )
               mistakeViewModel.saveJwtToken(this@SignUpActivity,response.body()?.jwt.toString())
               Log.d("s3",response.body().toString())
           }catch (e:Exception)
           {               Log.d("s3",e.toString())


           }

           val intent = Intent(this@SignUpActivity,LoginActivity::class.java)
           startActivity(intent)
        }    }



    @Composable
    fun SignUpScreen() {
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
            Button(onClick = { SignUp(username, password) }
            ) {
                Text("SignUp")
                Modifier
                    .background(Color.White, shape = RoundedCornerShape(25.dp))
                    .width(50.dp)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        SignUpScreen()
    }
}

