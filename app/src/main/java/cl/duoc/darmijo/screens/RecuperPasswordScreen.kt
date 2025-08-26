package cl.duoc.darmijo.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cl.duoc.darmijo.R
import cl.duoc.darmijo.data.Usuarios
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun RecuperaPasswordScreen(navController: NavController,  modifier: Modifier = Modifier) {
    var rut by remember { mutableStateOf("5-5") }
    var nroDocumento by remember { mutableStateOf("789456123") }
    var password by remember { mutableStateOf("Password1@") }
    var confirmPassword by remember { mutableStateOf("Password1@") }
    // Implement your login screen UI here
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cute_boy_running))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1.0f,
            restartOnPlay = false
        )
        LottieAnimation(
            composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        )
        Text(
            text = "Recupera Contraseña!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = rut,
            onValueChange = { rut = it },
            label = { Text("RUT") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = nroDocumento,
            onValueChange = { nroDocumento = it },
            label = { Text("Nro. Documento") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Repite Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                try {
                    Usuarios.tryRecuperaPassword(
                        rut = rut,
                        nroDocumento = nroDocumento,
                        plainPassword = password,
                        plainConfirmPassword = confirmPassword
                    )

                    navController.navigate("login") {
                        popUpTo("registro")
                    }
                    Log.d("RegistroScreen", "Registro exitoso")
                } catch (e: Exception) {
                    Toast.makeText(
                        navController.context,
                        "Error en registro: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("RegistroScreen", "Error en registro: ${e.message}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Cambiar Contraseña")
        }
    }
}
