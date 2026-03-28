package com.example.qme_mobile.presentation.dashboard

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qme_mobile.data.local.SessionManager
import com.example.qme_mobile.data.model.DashboardData
import com.example.qme_mobile.data.network.ApiResult
import com.example.qme_mobile.data.network.RetrofitClient
import com.example.qme_mobile.data.repository.AuthRepository
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val repository = AuthRepository(RetrofitClient.instance)

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var dashboardData by mutableStateOf<DashboardData?>(null)
    var userName by mutableStateOf("")

    fun loadDashboard(context: Context) {
        val session = SessionManager(context)
        val token = session.getToken() ?: return
        userName = session.getUserName()

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            when (val result = repository.getDashboard(token)) {
                is ApiResult.Success -> {
                    dashboardData = result.data.data
                }
                is ApiResult.Error -> {
                    // Dashboard data is optional — show fallback UI even on error
                    errorMessage = result.message
                }
                ApiResult.Loading -> {}
            }
            isLoading = false
        }
    }

    fun logout(context: Context, onLogout: () -> Unit) {
        SessionManager(context).clearSession()
        onLogout()
    }
}
