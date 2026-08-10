package vitorcode.school_proof_corrector.core.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vitorcode.school_proof_corrector.core.ui.events.BulletinEvent
import vitorcode.school_proof_corrector.core.ui.states.BulletinUiState
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BulletinViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _state = MutableStateFlow(BulletinUiState())
    val state = _state.asStateFlow()
    private val _events = MutableSharedFlow<BulletinEvent>()
    val events = _events.asSharedFlow()

    val studentId: UUID = UUID.fromString(
        checkNotNull(savedStateHandle["studentId"])
    )

    fun onClickNavigateToCreateBulletin() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    studentId = studentId
                )
            }
            _events.emit(BulletinEvent.NavigateToCreateBulletin)
        }
    }

}
