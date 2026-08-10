package vitorcode.school_proof_corrector.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import vitorcode.school_proof_corrector.core.ui.events.BulletinEvent
import vitorcode.school_proof_corrector.core.ui.viewModels.BulletinViewModel

@Composable
fun BulletinScreen(
    navController: NavController,
    viewModel: BulletinViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    BulletinEvent.NavigateToCreateBulletin -> {
                        navController
                            .navigate(
                                "create_bulletin_screen/${viewModel.state.value.studentId}")
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { /* TODO: Implementar ação do botão */ }) {
            Text("Visualizar Boletim")
        }

        Spacer(modifier = Modifier.height(12. dp))

        Button(onClick = viewModel::onClickNavigateToCreateBulletin) {
            Text("Adicionar Nota")
        }
    }
}
