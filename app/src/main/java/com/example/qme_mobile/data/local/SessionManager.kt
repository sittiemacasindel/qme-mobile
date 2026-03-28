package com.example.qme_mobile.data.local

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "QmeSession"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_PHONE = "user_phone"
        private const val KEY_USER_ADDRESS = "user_address"
    }

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun isLoggedIn(): Boolean = !getToken().isNullOrBlank()

    fun saveUserName(name: String) =
        prefs.edit().putString(KEY_USER_NAME, name).apply()

    fun getUserName(): String =
        prefs.getString(KEY_USER_NAME, "") ?: ""

    fun saveUserEmail(email: String) =
        prefs.edit().putString(KEY_USER_EMAIL, email).apply()

    fun getUserEmail(): String =
        prefs.getString(KEY_USER_EMAIL, "") ?: ""

    fun saveUserPhone(phone: String?) =
        prefs.edit().putString(KEY_USER_PHONE, phone ?: "").apply()

    fun getUserPhone(): String =
        prefs.getString(KEY_USER_PHONE, "") ?: ""

    fun saveUserAddress(address: String?) =
        prefs.edit().putString(KEY_USER_ADDRESS, address ?: "").apply()

    fun getUserAddress(): String =
        prefs.getString(KEY_USER_ADDRESS, "") ?: ""

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
