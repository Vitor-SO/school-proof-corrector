package vitorcode.school_proof_corrector.core.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vitorcode.school_proof_corrector.core.domain.model.SubjectResponseFilter
import vitorcode.school_proof_corrector.core.ui.states.SubjectUiState
import vitorcode.school_proof_corrector.data.repositories.SubjectRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepositoryImpl
): ViewModel() {
    private val _state = MutableStateFlow(SubjectUiState())
    val state = _state.asStateFlow()

    fun loadSubjects(filter: SubjectResponseFilter?) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val subjectList = subjectRepository.read(filter)
                _state.update {
                    it.copy(
                        subjects = subjectList,
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

    fun loadSubjectsByTeacherId() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val subjectList = subjectRepository.getSubjectsByTeacherId()
                _state.update {
                    it.copy(
                        subjects = subjectList,
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
}
