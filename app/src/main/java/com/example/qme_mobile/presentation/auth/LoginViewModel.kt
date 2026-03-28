package com.example.qme_mobile.presentation.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qme_mobile.data.local.SessionManager
import com.example.qme_mobile.data.network.ApiResult
import com.example.qme_mobile.data.network.RetrofitClient
import com.example.qme_mobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository(RetrofitClient.instance)

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)

    fun login(context: Context, onSuccess: () -> Unit) {
        if (!validateInput()) return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            when (val result = repository.login(email.trim(), password)) {
                is ApiResult.Success -> {
                    val session = SessionManager(context)
                    session.saveToken(result.data.token)
                    result.data.user?.let { user ->
                        session.saveUserName(user.name)
                        session.saveUserEmail(user.email)
                        session.saveUserPhone(user.phone)
                        session.saveUserAddress(user.address)
                    }
                    loginSuccess = true
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
            email.isBlank() -> { errorMessage = "Email is required."; false }
            password.isBlank() -> { errorMessage = "Password is required."; false }
            else -> true
        }
    }

    fun clearError() {
        errorMessage = null
    }
}
