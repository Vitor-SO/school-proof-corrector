package vitorcode.school_proof_corrector.core.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vitorcode.school_proof_corrector.core.ui.intents.TeacherEvent
import vitorcode.school_proof_corrector.core.ui.states.TeacherUiState
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel @Inject constructor(
): ViewModel() {
    private val _state = MutableStateFlow(TeacherUiState())
    val state = _state.asStateFlow()
    private val _events = MutableSharedFlow<TeacherEvent>()
    val events = _events.asSharedFlow()

    fun onClickNavigateToStudents() {
       viewModelScope.launch {
              _events.emit(TeacherEvent.NavigateToStudents)
       }
    }
}
