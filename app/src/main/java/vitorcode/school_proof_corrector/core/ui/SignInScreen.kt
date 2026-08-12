package vitorcode.school_proof_corrector.core.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import vitorcode.school_proof_corrector.core.ui.events.SignInUiEvent
import vitorcode.school_proof_corrector.core.ui.viewModels.SignInViewModel

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SignInUiEvent.NavigateToLogin -> {
                    navController.navigate("login"){
                        popUpTo("login"){inclusive = true}
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = state.slug,
            onValueChange = viewModel::onSlugChange,
            label = {
                Text("Identificador")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::onNameChange,
            label = {
                Text("Nome")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.userEmail,
            onValueChange = viewModel::onUserEmailChange,
            label = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.userPassword,
            onValueChange = viewModel::onUserPasswordChange,
            label = {
                Text("Senha")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.signIn()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Criar conta")
        }

        if (state.isSuccess) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Conta criada com sucesso",
                color = Color.Green
            )
        }

        state.error?.let {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = it,
                color = Color.Red
            )
        }
    }
}
