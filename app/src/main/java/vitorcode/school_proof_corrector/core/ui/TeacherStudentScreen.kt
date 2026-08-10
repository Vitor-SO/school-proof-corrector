package vitorcode.school_proof_corrector.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import vitorcode.school_proof_corrector.core.ui.intents.TeacherStudentIntent
import vitorcode.school_proof_corrector.core.ui.viewModels.TeacherStudentViewModel
import androidx.compose.material3.ListItem
import vitorcode.school_proof_corrector.core.ui.events.TeacherStudentEvent

@Composable
fun TeacherStudentScreen(
    navController: NavController,
    viewModel: TeacherStudentViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onStudentIntent(
            TeacherStudentIntent.LoadStudents
        )
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event){
                TeacherStudentEvent.NavigateToBulletin -> {
                    navController.navigate("bulletin_screen/${state.studentId}")
                }
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = state.studentSearched,
                onValueChange = viewModel::onStudentNameChange,
                label = {
                    Text("Digite o nome do aluno")
                },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {viewModel.searchStudent() },
            ) {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.width(8.dp))
        when {

            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(state.error!!)
            }

            else -> {

                state.studentsPaged?.let { page ->
                    LazyColumn {
                        items(
                            items = page.items,
                            key = { it.id }
                        ) { student ->

                            ListItem(
                                headlineContent = {
                                    Text(student.name)
                                },
                                supportingContent = {
                                    Text(
                                        buildString {
                                            student.className?.let {
                                                append(it)
                                            }

                                            student.academicLevel?.let {
                                                if (isNotEmpty()) append(" • ")
                                                append(it)
                                            }
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = {
                                        viewModel.onClickNavigateToBulletin(
                                            student.id
                                        )
                                    })
                            )

                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}