package com.example.qme_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.qme_mobile.data.local.SessionManager
import com.example.qme_mobile.presentation.navigation.QmeNavGraph
import com.example.qme_mobile.presentation.navigation.Routes
import com.example.qme_mobile.ui.theme.Qme_mobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Qme_mobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val session = SessionManager(this)
                    val startDestination = if (session.isLoggedIn()) Routes.DASHBOARD else Routes.LOGIN

                    QmeNavGraph(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}