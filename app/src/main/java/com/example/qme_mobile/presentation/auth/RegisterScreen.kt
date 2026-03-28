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
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val viewModel: RegisterViewModel = viewModel()
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
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Logo / Header
            Text(
                text = "QMe",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF6C63FF)
            )
            Text(
                text = "Queue Management",
                fontSize = 13.sp,
                color = Color(0xFF9E9E9E),
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F3460).copy(alpha = 0.8f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Join QMe to manage your queues",
                        fontSize = 13.sp,
                        color = Color(0xFF9E9E9E),
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                    )

                    // Error/Success
                    viewModel.errorMessage?.let {
                        ErrorMessageCard(message = it)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    viewModel.successMessage?.let {
                        SuccessMessageCard(message = it)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Fields
                    QmeTextField(
                        value = viewModel.name,
                        onValueChange = { viewModel.name = it; viewModel.clearMessages() },
                        label = "Full Name",
                        enabled = !viewModel.isLoading
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    QmeTextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.email = it; viewModel.clearMessages() },
                        label = "Email Address",
                        keyboardType = KeyboardType.Email,
                        enabled = !viewModel.isLoading
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    QmeTextField(
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it; viewModel.clearMessages() },
                        label = "Password",
                        isPassword = true,
                        enabled = !viewModel.isLoading
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    QmeTextField(
                        value = viewModel.confirmPassword,
                        onValueChange = { viewModel.confirmPassword = it; viewModel.clearMessages() },
                        label = "Confirm Password",
                        isPassword = true,
                        enabled = !viewModel.isLoading
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    QmeButton(
                        text = "Create Account",
                        onClick = { viewModel.register(onRegisterSuccess) },
                        isLoading = viewModel.isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = onNavigateToLogin) {
                        Text(
                            text = "Already have an account? Sign In",
                            color = Color(0xFF6C63FF),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
