package vitorcode.school_proof_corrector.core.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vitorcode.school_proof_corrector.core.ui.events.TeacherStudentEvent
import vitorcode.school_proof_corrector.core.ui.intents.TeacherStudentIntent
import vitorcode.school_proof_corrector.core.ui.states.TeacherStudentUiState
import vitorcode.school_proof_corrector.data.repositories.StudentRepositoryImpl
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class TeacherStudentViewModel @Inject constructor(
    private val studentRepository: StudentRepositoryImpl
): ViewModel() {
    private val _state = MutableStateFlow(TeacherStudentUiState())
    val state = _state.asStateFlow()
    private val _events = MutableSharedFlow<TeacherStudentEvent>()
    val events = _events.asSharedFlow()

    fun onStudentIntent(intent: TeacherStudentIntent){
        when(intent){
            TeacherStudentIntent.LoadStudents -> loadStudents()
        }
    }

    fun loadStudents(){
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val response = studentRepository.getStudents(null)
                _state.update {
                    it.copy(
                        studentsPaged = response,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun onStudentNameChange(value: String) {
        _state.update {
            it.copy(studentSearched = value)
        }
    }

    fun searchStudent() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val response = studentRepository.getStudents(_state.value.studentSearched)
                _state.update {
                    it.copy(
                        studentsPaged = response,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun onClickNavigateToBulletin(studentId: UUID){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    studentId = studentId,
                    isLoading = false,
                    error = null
                )
            }
            _events.emit(TeacherStudentEvent.NavigateToBulletin)
        }
    }

}