package com.example.qme_mobile.presentation.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qme_mobile.data.local.SessionManager
import com.example.qme_mobile.data.model.UserData
import com.example.qme_mobile.data.network.ApiResult
import com.example.qme_mobile.data.network.RetrofitClient
import com.example.qme_mobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repository = AuthRepository(RetrofitClient.instance)

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var userData by mutableStateOf<UserData?>(null)

    // Update Profile fields
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var address by mutableStateOf("")
    var updateSuccess by mutableStateOf<String?>(null)
    var isUpdating by mutableStateOf(false)

    // Change Password fields
    var currentPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var passwordSuccess by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var isChangingPassword by mutableStateOf(false)

    fun loadProfile(context: Context) {
        val session = SessionManager(context)
        val token = session.getToken() ?: return

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            when (val result = repository.getProfile(token)) {
                is ApiResult.Success -> {
                    val user = result.data.user
                    userData = user
                    user?.let {
                        name = it.name
                        email = it.email
                        phone = it.phone ?: ""
                        address = it.address ?: ""
                    }
                }
                is ApiResult.Error -> {
                    // Fallback to cached session data
                    name = session.getUserName()
                    email = session.getUserEmail()
                    phone = session.getUserPhone()
                    address = session.getUserAddress()
                    errorMessage = result.message
                }
                ApiResult.Loading -> {}
            }
            isLoading = false
        }
    }

    fun updateProfile(context: Context) {
        if (!validateUpdateInput()) return
        val session = SessionManager(context)
        val token = session.getToken() ?: return

        viewModelScope.launch {
            isUpdating = true
            errorMessage = null
            updateSuccess = null

            when (val result = repository.updateProfile(
                token, name.trim(), email.trim(),
                phone.ifBlank { null }, address.ifBlank { null }
            )) {
                is ApiResult.Success -> {
                    updateSuccess = result.data.message
                    result.data.user?.let { user ->
                        userData = user
                        session.saveUserName(user.name)
                        session.saveUserEmail(user.email)
                        session.saveUserPhone(user.phone)
                        session.saveUserAddress(user.address)
                    }
                }
                is ApiResult.Error -> errorMessage = result.message
                ApiResult.Loading -> {}
            }
            isUpdating = false
        }
    }

    fun changePassword(context: Context) {
        if (!validatePasswordInput()) return
        val session = SessionManager(context)
        val token = session.getToken() ?: return

        viewModelScope.launch {
            isChangingPassword = true
            passwordError = null
            passwordSuccess = null

            when (val result = repository.changePassword(
                token, currentPassword, newPassword, confirmPassword
            )) {
                is ApiResult.Success -> {
                    passwordSuccess = result.data.message
                    currentPassword = ""
                    newPassword = ""
                    confirmPassword = ""
                }
                is ApiResult.Error -> passwordError = result.message
                ApiResult.Loading -> {}
            }
            isChangingPassword = false
        }
    }

    private fun validateUpdateInput(): Boolean {
        return when {
            name.isBlank() -> { errorMessage = "Name is required."; false }
            email.isBlank() -> { errorMessage = "Email is required."; false }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                errorMessage = "Please enter a valid email address."; false
            }
            else -> true
        }
    }

    private fun validatePasswordInput(): Boolean {
        return when {
            currentPassword.isBlank() -> { passwordError = "Current password is required."; false }
            newPassword.length < 8 -> { passwordError = "New password must be at least 8 characters."; false }
            newPassword != confirmPassword -> { passwordError = "New passwords do not match."; false }
            else -> true
        }
    }

    fun clearMessages() {
        errorMessage = null
        updateSuccess = null
        passwordError = null
        passwordSuccess = null
    }
}
