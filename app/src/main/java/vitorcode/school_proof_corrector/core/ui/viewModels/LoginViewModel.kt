package vitorcode.school_proof_corrector.core.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vitorcode.school_proof_corrector.core.ui.events.LoginUiEvent
import vitorcode.school_proof_corrector.core.ui.states.LoginUiState
import vitorcode.school_proof_corrector.data.local.TokenManager
import vitorcode.school_proof_corrector.data.repositories.LoginRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepositoryImpl,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events = _events.asSharedFlow()

    fun onSlugChange(value: String) {
        _uiState.update {
            it.copy(slug = value)
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(userEmail = value)
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(password = value)
        }
    }

    fun login() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    loading = true,
                    error = null
                )
            }

            try {
                Log.d("loginviewmodel ", "${_uiState.value.slug}, ${_uiState.value.userEmail}, ${_uiState.value.password}")
                val response = repository.login(
                    _uiState.value.slug,
                    _uiState.value.userEmail,
                    _uiState.value.password
                )

                Log.d("loginviewmodel", "${response.token}")

                if(response.token.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            code = 500,
                            error = "Login Falhou: Token vazio"
                        )
                    }
                    return@launch
                }

                _uiState.update {
                    it.copy(loading = false)
                }

                tokenManager.saveAccessToken(response.token)

                _events.emit(LoginUiEvent.NavigateToHome)

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