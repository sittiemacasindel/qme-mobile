package com.example.qme_mobile.uifiles.login
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.qme_mobile.data.model.LoginRequest
import com.example.qme_mobile.data.model.LoginResponse
import com.example.qme_mobile.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                loading = true

                val request = LoginRequest(email, password)

                RetrofitClient.instance.login(request)
                    .enqueue(object : Callback<LoginResponse> {

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            loading = false

                            if (response.isSuccessful) {
                                val token = response.body()?.token

                                val pref = context.getSharedPreferences("APP", 0)
                                pref.edit().putString("TOKEN", token).apply()

                                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()

                                onLoginSuccess()
                            } else {
                                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            loading = false
                            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show()
                        }
                    })

            },
            enabled = !loading
        ) {
            Text(if (loading) "Loading..." else "Login")
        }
    }
}