package com.example.qme_mobile.data.remote

import com.example.qme_mobile.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // 🔐 AUTH

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>


    // 📊 DASHBOARD

    @GET("dashboard")
    suspend fun getDashboard(
        @Header("Authorization") token: String
    ): Response<DashboardResponse>


    // 👤 PROFILE

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<ProfileResponse>


    // ✏️ UPDATE PROFILE

    @PUT("profile/update")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): Response<UpdateProfileResponse>


    // 🔑 CHANGE PASSWORD

    @PUT("change-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body request: ChangePasswordRequest
    ): Response<ChangePasswordResponse>
}