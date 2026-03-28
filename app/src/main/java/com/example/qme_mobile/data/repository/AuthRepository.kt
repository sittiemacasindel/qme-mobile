package com.example.qme_mobile.data.repository

import com.example.qme_mobile.data.model.*
import com.example.qme_mobile.data.network.ApiResult
import com.example.qme_mobile.data.network.ApiService
import com.example.qme_mobile.data.network.getHttpErrorMessage
import com.example.qme_mobile.data.network.parseErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val apiService: ApiService) {

    private suspend fun <T> safeApiCall(call: suspend () -> retrofit2.Response<T>): ApiResult<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) ApiResult.Success(body)
                    else ApiResult.Error("Empty response from server", response.code())
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = parseErrorMessage(errorBody).ifBlank {
                        getHttpErrorMessage(response.code())
                    }
                    ApiResult.Error(message, response.code())
                }
            } catch (e: java.net.UnknownHostException) {
                ApiResult.Error("No internet connection. Please check your network.")
            } catch (e: java.net.ConnectException) {
                ApiResult.Error("Cannot connect to server. Please try again.")
            } catch (e: java.net.SocketTimeoutException) {
                ApiResult.Error("Connection timed out. Please try again.")
            } catch (e: Exception) {
                ApiResult.Error(e.message ?: "An unexpected error occurred.")
            }
        }

    suspend fun register(
        name: String, email: String, password: String, confirmPassword: String
    ) = safeApiCall {
        apiService.register(RegisterRequest(name, email, password, confirmPassword))
    }

    suspend fun login(email: String, password: String) = safeApiCall {
        apiService.login(LoginRequest(email, password))
    }

    suspend fun getProfile(token: String) = safeApiCall {
        apiService.getProfile("Bearer $token")
    }

    suspend fun updateProfile(
        token: String, name: String, email: String, phone: String?, address: String?
    ) = safeApiCall {
        apiService.updateProfile("Bearer $token", UpdateProfileRequest(name, email, phone, address))
    }

    suspend fun changePassword(
        token: String, currentPassword: String, newPassword: String, confirmPassword: String
    ) = safeApiCall {
        apiService.changePassword(
            "Bearer $token",
            ChangePasswordRequest(currentPassword, newPassword, confirmPassword)
        )
    }

    suspend fun getDashboard(token: String) = safeApiCall {
        apiService.getDashboard("Bearer $token")
    }
}
