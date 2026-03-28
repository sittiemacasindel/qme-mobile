# QMe Mobile — Queue Management System

An Android application built with **Jetpack Compose** and **Retrofit** that connects to a backend REST API for user authentication and queue management.

---

## 📱 Features

| Screen | Description |
|--------|-------------|
| **Register** | Creates a new account with name, email, and password |
| **Login** | Authenticates the user and stores the Bearer token |
| **Dashboard** | Displays queue statistics fetched from the API |
| **Profile** | Shows user information fetched from the API |
| **Update Profile** | Edits and saves name, email, phone, and address |
| **Change Password** | Updates the current password securely |

---

## 🛠️ Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material3
- **Networking:** Retrofit 2.9.0 + OkHttp Logging Interceptor
- **JSON Parsing:** Gson Converter
- **Navigation:** Navigation Compose
- **Architecture:** MVVM (ViewModel + Repository)
- **State:** Compose `mutableStateOf`
- **Auth Storage:** SharedPreferences (`SessionManager`)

---

## 🌐 API Integration

### Base URL
Replace `BASE_URL` in `RetrofitClient.kt`:
```kotlin
private const val BASE_URL = "https://your-api-url.com/"
```

### Endpoints

| Method | Endpoint | Auth | Description |
|--------|---------|------|-------------|
| POST | `/api/register` | None | Register new user |
| POST | `/api/login` | None | Login and get token |
| GET | `/api/profile` | Bearer | Get user profile |
| PUT | `/api/profile` | Bearer | Update user profile |
| PUT | `/api/change-password` | Bearer | Change password |
| GET | `/api/dashboard` | Bearer | Get dashboard stats |

### Authentication
All protected routes require:
```
Authorization: Bearer <token>
```
The token is saved in `SharedPreferences` after login and automatically included in every protected request.

---

## ⚙️ Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/your-username/qme-mobile.git
cd qme-mobile
```

### 2. Set your API base URL
Open `app/src/main/java/com/example/qme_mobile/data/network/RetrofitClient.kt` and replace:
```kotlin
private const val BASE_URL = "https://your-api-url.com/"
```

### 3. Sync Gradle
In Android Studio, click **Sync Now** after opening the project.

### 4. Run
Connect a device or start an emulator, then press **Run ▶**.

---

## 📦 Dependencies (app/build.gradle.kts)

```kotlin
// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// OkHttp Logging
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Navigation Compose
implementation("androidx.navigation:navigation-compose:2.7.7")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

---

## 🗂️ Project Structure

```
app/src/main/java/com/example/qme_mobile/
├── MainActivity.kt
├── data/
│   ├── local/
│   │   └── SessionManager.kt          # SharedPreferences token storage
│   ├── model/
│   │   └── Models.kt                  # Request/Response data classes
│   ├── network/
│   │   ├── ApiService.kt              # Retrofit interface
│   │   ├── RetrofitClient.kt          # Singleton Retrofit instance
│   │   └── ApiResult.kt              # Sealed class + error helpers
│   └── repository/
│       └── AuthRepository.kt          # All API calls + error handling
└── presentation/
    ├── auth/
    │   ├── LoginScreen.kt
    │   ├── LoginViewModel.kt
    │   ├── RegisterScreen.kt
    │   └── RegisterViewModel.kt
    ├── dashboard/
    │   ├── DashboardScreen.kt
    │   └── DashboardViewModel.kt
    ├── navigation/
    │   ├── QmeNavGraph.kt
    │   └── Routes.kt
    ├── profile/
    │   ├── ProfileScreen.kt
    │   ├── UpdateProfileScreen.kt
    │   ├── ChangePasswordScreen.kt
    │   └── ProfileViewModel.kt
    └── ui/components/
        └── CommonComponents.kt        # Shared UI components
```

---

## ❌ Error Handling

| Scenario | Handling |
|----------|----------|
| No internet | "No internet connection. Please check your network." |
| Timeout | "Connection timed out. Please try again." |
| 400 Bad Request | "Bad request. Please check your input." |
| 401 Unauthorized | "Unauthorized. Please log in again." |
| 422 Validation | Shows individual field validation messages from API |
| 500 Server Error | "Server error. Please try again later." |

All errors are shown in a red `ErrorMessageCard`. Buttons are disabled during loading. A `CircularProgressIndicator` is shown instead of button text.

---

## 📸 Screenshots

> Add screenshots here after running the app.

| Register | Login | Dashboard |
|----------|-------|-----------|
| _(screenshot)_ | _(screenshot)_ | _(screenshot)_ |

| Profile | Update Profile | Change Password |
|---------|----------------|-----------------|
| _(screenshot)_ | _(screenshot)_ | _(screenshot)_ |

---

## 🔐 Security Notes

- Tokens are stored in `SharedPreferences` (MODE_PRIVATE)
- Passwords are masked with toggle visibility
- Session is cleared entirely on logout
- `usesCleartextTraffic="true"` is set for development — **disable in production** and use HTTPS only

---

## 📄 License

MIT License © 2026 QMe Team
