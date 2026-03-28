package com.example.qme_mobile.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qme_mobile.data.network.ApiResult
import com.example.qme_mobile.data.network.RetrofitClient
import com.example.qme_mobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val repository = AuthRepository(RetrofitClient.instance)

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var successMessage by mutableStateOf<String?>(null)
    var registrationSuccess by mutableStateOf(false)

    fun register(onSuccess: () -> Unit) {
        if (!validateInput()) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            successMessage = null

            when (val result = repository.register(name.trim(), email.trim(), password, confirmPassword)) {
                is ApiResult.Success -> {
                    successMessage = result.data.message
                    registrationSuccess = true
                    onSuccess()
                }
                is ApiResult.Error -> {
                    errorMessage = result.message
                }
                ApiResult.Loading -> {}
            }
            isLoading = false
        }
    }

    private fun validateInput(): Boolean {
        return when {
            name.isBlank() -> { errorMessage = "Name is required."; false }
            email.isBlank() -> { errorMessage = "Email is required."; false }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                errorMessage = "Please enter a valid email address."; false
            }
            password.length < 8 -> { errorMessage = "Password must be at least 8 characters."; false }
            password != confirmPassword -> { errorMessage = "Passwords do not match."; false }
            else -> true
        }
    }

    fun clearMessages() {
        errorMessage = null
        successMessage = null
    }
}
