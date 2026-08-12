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
import vitorcode.school_proof_corrector.core.domain.model.SignInRequest
import vitorcode.school_proof_corrector.core.ui.events.SignInUiEvent
import vitorcode.school_proof_corrector.core.ui.states.SignInUiState
import vitorcode.school_proof_corrector.data.repositories.SignInRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: SignInRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()
    private val _events = MutableSharedFlow<SignInUiEvent>()
    val events = _events.asSharedFlow()


    fun onSlugChange(value: String) {
        _uiState.update {
            it.copy(slug = value)
        }
    }

    fun onNameChange(value: String) {
        _uiState.update {
            it.copy(name = value)
        }
    }

    fun onUserEmailChange(value: String) {
        _uiState.update {
            it.copy(userEmail = value)
        }
    }

    fun onUserPasswordChange(value: String) {
        _uiState.update {
            it.copy(userPassword = value)
        }
    }

    fun signIn() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    loading = true,
                    error = null
                )
            }

            try {
                val response = repository.signIn(
                    SignInRequest(
                        slug = _uiState.value.slug,
                        name = _uiState.value.name,
                        userEmail = _uiState.value.userEmail,
                        userPassword = _uiState.value.userPassword
                    )
                )

                if(!response.isSuccessful){
                    _uiState.update {
                        it.copy(
                            loading = false,
                            isSuccess = false,
                            code = 500,
                            error = "Erro ao criar conta"
                        )
                    }

                    return@launch
                }

                _uiState.update {
                    it.copy(
                        loading = false,
                        isSuccess = true
                    )
                }

                _events.emit(SignInUiEvent.NavigateToLogin)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}
