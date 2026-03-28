package com.example.qme_mobile.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qme_mobile.presentation.ui.components.*

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val viewModel: LoginViewModel = viewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A1A2E), Color(0xFF16213E))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Text(
                text = "QMe",
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF6C63FF)
            )
            Text(
                text = "Queue Management System",
                fontSize = 12.sp,
                color = Color(0xFF9E9E9E),
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F3460).copy(alpha = 0.85f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome Back",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Sign in to your account",
                        fontSize = 13.sp,
                        color = Color(0xFF9E9E9E),
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                    )

                    viewModel.errorMessage?.let {
                        ErrorMessageCard(message = it)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    QmeTextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.email = it; viewModel.clearError() },
                        label = "Email Address",
                        keyboardType = KeyboardType.Email,
                        enabled = !viewModel.isLoading
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    QmeTextField(
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it; viewModel.clearError() },
                        label = "Password",
                        isPassword = true,
                        enabled = !viewModel.isLoading
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    QmeButton(
                        text = "Sign In",
                        onClick = { viewModel.login(context, onLoginSuccess) },
                        isLoading = viewModel.isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = onNavigateToRegister) {
                        Text(
                            text = "Don't have an account? Register",
                            color = Color(0xFF6C63FF),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
