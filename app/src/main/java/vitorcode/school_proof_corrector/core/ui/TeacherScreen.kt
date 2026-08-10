package vitorcode.school_proof_corrector.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import vitorcode.school_proof_corrector.core.ui.intents.TeacherEvent
import vitorcode.school_proof_corrector.core.ui.viewModels.TeacherViewModel

@Composable
fun TeacherScreen(
    navController: NavController,
    viewModel: TeacherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                TeacherEvent.NavigateToStudents -> {
                    navController.navigate("teacher_student_screen")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { /* TODO: Implementar ação do botão */ }) {
            Text("Turmas")
        }

        Spacer(modifier = Modifier.height(12. dp))

        Button(onClick = { /* TODO: Implementar ação do botão */ }) {
            Text("Disciplinas")
        }

        Spacer(modifier = Modifier.height(12. dp))

        Button(onClick = viewModel::onClickNavigateToStudents) {
            Text("Alunos")
        }
    }
}