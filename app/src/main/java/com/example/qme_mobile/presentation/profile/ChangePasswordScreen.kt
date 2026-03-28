package com.example.qme_mobile.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qme_mobile.presentation.ui.components.*

@Composable
fun ChangePasswordScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel: ProfileViewModel = viewModel()
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
                .padding(horizontal = 20.dp, vertical = 40.dp)
        ) {
            // Top bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF0F3460))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Change Password",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F3460))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Choose a strong password (min. 8 characters)",
                        color = Color(0xFF9E9E9E),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    viewModel.passwordError?.let {
                        ErrorMessageCard(message = it)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    viewModel.passwordSuccess?.let {
                        SuccessMessageCard(message = it)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    QmeTextField(
                        value = viewModel.currentPassword,
                        onValueChange = { viewModel.currentPassword = it; viewModel.clearMessages() },
                        label = "Current Password",
                        isPassword = true,
                        enabled = !viewModel.isChangingPassword
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    QmeTextField(
                        value = viewModel.newPassword,
                        onValueChange = { viewModel.newPassword = it; viewModel.clearMessages() },
                        label = "New Password",
                        isPassword = true,
                        enabled = !viewModel.isChangingPassword
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    QmeTextField(
                        value = viewModel.confirmPassword,
                        onValueChange = { viewModel.confirmPassword = it; viewModel.clearMessages() },
                        label = "Confirm New Password",
                        isPassword = true,
                        enabled = !viewModel.isChangingPassword
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    QmeButton(
                        text = "Update Password",
                        onClick = { viewModel.changePassword(context) },
                        isLoading = viewModel.isChangingPassword,
                        containerColor = Color(0xFFFF9800)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Security tips card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F3460))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "🔐 Security Tips",
                        color = Color(0xFFFF9800),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    listOf(
                        "Use at least 8 characters",
                        "Include numbers and symbols",
                        "Avoid using personal info",
                        "Don't reuse old passwords"
                    ).forEach { tip ->
                        Text("• $tip", color = Color(0xFF9E9E9E), fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
