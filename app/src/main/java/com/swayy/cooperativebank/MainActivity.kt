package com.swayy.cooperativebank

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.swayy.cooperativebank.domain.model.Login
import com.swayy.cooperativebank.presentation.screens.HomeScreen
import com.swayy.cooperativebank.presentation.screens.LoginScreen
import com.swayy.cooperativebank.presentation.ui.theme.CooperativeBankTheme
import com.swayy.cooperativebank.util.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CooperativeBankTheme {
                val navController = rememberAnimatedNavController()

                CompositionLocalProvider() {
                    Scaffold(
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = Route.HOME
                        ) {
                            composable(route = Route.HOME) {

                                LoginScreen(navigateToWelcomeScreen = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(key = "test",value = it)
                                    navController.navigate(Route.HOME_SCREEN)
                                })
                            }

                            composable(route = Route.HOME_SCREEN) {
                                val user = navController.previousBackStackEntry?.savedStateHandle?.get<Login>(key ="user")
                                HomeScreen(
                                    userTest = user
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}