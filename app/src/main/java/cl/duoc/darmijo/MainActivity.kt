package cl.duoc.darmijo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.darmijo.screens.HomeScreen
import cl.duoc.darmijo.ui.theme.AppTheme
import cl.duoc.darmijo.screens.LoginScreen
import cl.duoc.darmijo.screens.RecuperaPasswordScreen
import cl.duoc.darmijo.screens.RegistroScreen
import cl.duoc.darmijo.ui.theme.Typography

class MainActivity : ComponentActivity() {
    private val HighContrastColorScheme = darkColorScheme(
        primary = Color.Black,
        onPrimary = Color.White,
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.Black,
        onSurface = Color.Black
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController();
                    NavHost(navController = navController, startDestination = "login") {
                        composable(route = "login") {
                            LoginScreen(navController, Modifier.padding(innerPadding))
                        }
                        composable(route = "registro") {
                            RegistroScreen(navController, Modifier.padding(innerPadding))
                        }
                        composable(route = "recover") {
                            RecuperaPasswordScreen(navController, Modifier.padding(innerPadding))
                        }
                        composable(
                            route = "home/{uid}",
                            arguments = listOf(navArgument("uid") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val uid = backStackEntry.arguments?.getString("uid");
                            if (uid != null) {
                                HomeScreen(navController, uid)
                            } else {
                                LoginScreen(navController, Modifier.padding(innerPadding))
                            }


                        }
                    }
                }
            }
        }
    }
    @Composable
    fun AppTheme(content: @Composable () -> Unit) {
        MaterialTheme(
            colorScheme = HighContrastColorScheme,
            content = content,
            typography = Typography,
        )
    }
}

