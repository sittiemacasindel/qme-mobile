package com.example.qme_mobile.data.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)

data class RegisterResponse(
    val message: String,
    val user: UserData? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val message: String,
    val user: UserData? = null
)

data class UserData(
    val id: Int? = null,
    val name: String = "",
    val email: String = "",
    val phone: String? = null,
    val address: String? = null,
    val created_at: String? = null
)

data class ProfileResponse(
    val message: String? = null,
    val user: UserData? = null
)

data class UpdateProfileRequest(
    val name: String,
    val email: String,
    val phone: String? = null,
    val address: String? = null
)

data class UpdateProfileResponse(
    val message: String,
    val user: UserData? = null
)

data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String,
    val new_password_confirmation: String
)

data class ChangePasswordResponse(
    val message: String
)

data class DashboardResponse(
    val message: String? = null,
    val data: DashboardData? = null
)

data class DashboardData(
    val total_queues: Int = 0,
    val active_queues: Int = 0,
    val completed_today: Int = 0,
    val average_wait_time: String = "N/A"
)

data class ApiErrorResponse(
    val message: String,
    val errors: Map<String, List<String>>? = null
)
