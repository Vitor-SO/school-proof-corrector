package vitorcode.school_proof_corrector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import vitorcode.school_proof_corrector.core.ui.HomeScreen
import vitorcode.school_proof_corrector.core.ui.LoginScreen
import vitorcode.school_proof_corrector.core.ui.SignInScreen
import vitorcode.school_proof_corrector.data.local.TokenManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @Inject lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val startDestination = when{
                tokenManager.getToken() == null -> "signin"
                !hasValidToken() -> "login"
                else -> "login"
            }


            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable("signin"){
                    SignInScreen(navController)
                }
                composable("login") {
                    LoginScreen(navController)
                }
                composable("home") {
                    HomeScreen(navController)
                }
            }
        }
    }

    private fun hasValidToken(): Boolean {
        return tokenManager.getToken()?.isNotEmpty() == true
    }
}