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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qme_mobile.presentation.ui.components.*

@Composable
fun UpdateProfileScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel: ProfileViewModel = viewModel()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        if (viewModel.name.isBlank()) viewModel.loadProfile(context)
    }

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
                    text = "Update Profile",
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
                        text = "Edit your information",
                        color = Color(0xFF9E9E9E),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    viewModel.errorMessage?.let {
                        ErrorMessageCard(message = it)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    viewModel.updateSuccess?.let {
                        SuccessMessageCard(message = it)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (viewModel.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF6C63FF))
                        }
                    } else {
                        QmeTextField(
                            value = viewModel.name,
                            onValueChange = { viewModel.name = it; viewModel.clearMessages() },
                            label = "Full Name",
                            enabled = !viewModel.isUpdating
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        QmeTextField(
                            value = viewModel.email,
                            onValueChange = { viewModel.email = it; viewModel.clearMessages() },
                            label = "Email Address",
                            keyboardType = KeyboardType.Email,
                            enabled = !viewModel.isUpdating
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        QmeTextField(
                            value = viewModel.phone,
                            onValueChange = { viewModel.phone = it; viewModel.clearMessages() },
                            label = "Phone Number (Optional)",
                            keyboardType = KeyboardType.Phone,
                            enabled = !viewModel.isUpdating
                        )
                        Spacer(modifier = Modifier.height(14.dp))

                        QmeTextField(
                            value = viewModel.address,
                            onValueChange = { viewModel.address = it; viewModel.clearMessages() },
                            label = "Address (Optional)",
                            enabled = !viewModel.isUpdating,
                            singleLine = false
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        QmeButton(
                            text = "Save Changes",
                            onClick = { viewModel.updateProfile(context) },
                            isLoading = viewModel.isUpdating
                        )
                    }
                }
            }
        }
    }
}
