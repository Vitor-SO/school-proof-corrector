package vitorcode.school_proof_corrector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import vitorcode.school_proof_corrector.core.domain.model.UserRole
import vitorcode.school_proof_corrector.core.ui.BulletinScreen
import vitorcode.school_proof_corrector.core.ui.CreateBulletinScreen
import vitorcode.school_proof_corrector.core.ui.TeacherStudentScreen
import vitorcode.school_proof_corrector.core.ui.LoginScreen
import vitorcode.school_proof_corrector.core.ui.TeacherScreen
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
                tokenManager.getUserRole() == UserRole.professor -> "teacher_screen"
                tokenManager.getUserRole() == UserRole.aluno -> "student_screen"
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

                composable("teacher_screen"){
                    TeacherScreen(navController)
                }

                composable("teacher_student_screen") {
                    TeacherStudentScreen(navController)
                }

                composable(
                    route = "bulletin_screen/{studentId}",
                    arguments = listOf(navArgument("studentId") { type = NavType.StringType })
                ) {
                    BulletinScreen(navController)
                }

                composable(
                    route = "create_bulletin_screen/{studentId}",
                    arguments = listOf(navArgument("studentId"){type = NavType.StringType})
                ) {
                    CreateBulletinScreen(navController)
                }
            }
        }
    }

    private fun hasValidToken(): Boolean {
        return tokenManager.getToken()?.isNotEmpty() == true
    }
}